
document.addEventListener('DOMContentLoaded', function () {
    mapboxgl.accessToken =
        'pk.eyJ1IjoiZ3J1cG8tMjItZGRzIiwiYSI6ImNtNHVmMnNlczBsaDAya29wNW91M2gyZzQifQ.5zb0wvUU8_k9rZfgg0yxXA';
    var map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/grupo-22-dds/cm4ugrx2j000001qp8wra1nz4',
        center: [-58.4370894, -34.6075682], // Coordenadas de Buenos Aires
        zoom: 13,
    });

    var marker;
    var polygon;

    obtenerHeladeras().then(heladeras => {
        console.log('Heladeras', heladeras);

        map.on('load', function () {

            map.addSource('heladeras', {
                type: 'geojson',
                data: {
                    type: "FeatureCollection",
                    features: heladeras.map(heladera => ({
                        type: "Feature",
                        properties: {
                            id: heladera.id,
                            nombre: heladera.nombre,
                            barrio: heladera.barrio,
                            calleYAltura: heladera.calleYAltura,
                            isActive: heladera.estado === "ACTIVA" ? 1 : 0,
                            capacidad: heladera.capacidad,
                            disponibilidad: heladera.cantViandas,
                            temperatura_max: heladera.temperaturaMaxima,
                            temperatura_min: heladera.temperaturaMinima
                        },
                        geometry: {
                            type: "Point",
                            coordinates: [
                                heladera.latitud,
                                heladera.longitud
                            ]
                        }
                    }))
                }
            });

            map.addLayer({
                id: "heladeras",
                type: "circle",
                source: "heladeras",
                paint: {
                    "circle-color": [
                        "case",
                        ["==", ["get", "isActive"], 1], "hsl(120, 45%, 58%)", // Activa
                        "hsl(0, 55%, 54%)" // Inactiva
                    ],
                    "circle-radius": 10,
                    "circle-stroke-color": "hsl(0, 0%, 0%)",
                    "circle-stroke-width": 1
                }
            });

            let selectedFeatureId = null;

            map.on('mousemove', 'heladeras', function (e) {
                const featureId = e.features[0].properties.id;
                map.setPaintProperty('heladeras', 'circle-color', [
                    'case',
                    ['==', ['get', 'id'], featureId],
                    ['case',
                        ['==', ['get', 'isActive'], 1], "hsl(120, 47%, 70%)", // Hover Activa
                        "hsl(0, 61%, 73%)" // Hover Inactiva
                    ],
                    ["==", ["get", "isActive"], 1], "hsl(120, 45%, 58%)",
                    "hsl(0, 55%, 54%)"
                ]);
            });

            map.on('mouseleave', 'heladeras', function () {
                if (!selectedFeatureId) {
                    map.setPaintProperty('heladeras', 'circle-color', [
                        "case",
                        ["==", ["get", "isActive"], 1], "hsl(120, 45%, 58%)",
                        "hsl(0, 55%, 54%)"
                    ]);
                }
            });

            map.on('click', 'heladeras', function (e) {
                const featureId = e.features[0].properties.id;
                selectedFeatureId = featureId;
                map.setPaintProperty('heladeras', 'circle-color', [
                    'case',
                    ['==', ['get', 'id'], featureId], "hsl(49, 79%, 69%)", // Click
                    ["==", ["get", "isActive"], 1], "hsl(120, 45%, 58%)",
                    "hsl(0, 55%, 54%)"
                ]);
            });
        });

        async function obtenerHeladeras() {
            try {
                const response = await fetch('https://localhost/heladeras');
                if (!response.ok) {
                    throw new Error(`Error en la solicitud: ${response.status}`);
                }
                const heladeras = await response.json();
                return heladeras;
            } catch (error) {
                console.error('Error al obtener las heladeras:', error);
                return [];
            }
        }

        function searchLocation() {
            var location = document.getElementById('search').value;
            fetch(
                `https://api.mapbox.com/geocoding/v5/mapbox.places/${location}.json?access_token=${mapboxgl.accessToken}&country=AR`
            )
                .then((response) => response.json())
                .then((data) => {
                    if (data.features.length > 0) {
                        var feature = data.features[0];
                        var lat = feature.center[1];
                        var lon = feature.center[0];
                        map.setCenter([lon, lat]);
                        map.setZoom(14);

                        if (marker) marker.remove();
                        if (polygon) {
                            map.removeLayer('polygon');
                            map.removeSource('polygon');
                        }

                        if (feature.place_type.includes('address')) {
                            marker = new mapboxgl.Marker().setLngLat([lon, lat]).addTo(map);
                        }
                    } else {
                        alert('Ubicación no encontrada');
                    }
                })
                .catch((error) => console.error('Error:', error));
        }
    });
})
