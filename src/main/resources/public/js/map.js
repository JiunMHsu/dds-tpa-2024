document.addEventListener('DOMContentLoaded', function () {
    mapboxgl.accessToken = 'pk.eyJ1IjoiZ3J1cG8tMjItZGRzIiwiYSI6ImNtNHVmMnNlczBsaDAya29wNW91M2gyZzQifQ.5zb0wvUU8_k9rZfgg0yxXA';
    const map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/grupo-22-dds/cm4ugrx2j000001qp8wra1nz4',
        center: [-58.4370894, -34.6075682], // Coordenadas de Buenos Aires
        zoom: 13,
    });

    let selectedFeatureId = null;

    fetch('./heladeras.json') // TODO - Cambiar la ruta
        .then((response) => {
            if (!response.ok) {
                throw new Error(`Error al cargar las heladeras: ${response.statusText}`);
            }
            return response.json();
        })
        .then((geojsonData) => {
            map.on('load', function () {
                map.addSource('heladeras', {
                    type: 'geojson',
                    data: geojsonData,
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

                map.on('mousemove', 'heladeras', function (e) {
                    if (!e.features || e.features.length === 0) return;
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
                    if (!e.features || e.features.length === 0) return;
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

            const bounds = new mapboxgl.LngLatBounds();
            geojsonData.features.forEach((feature) => {
                bounds.extend(feature.geometry.coordinates);
            });
            map.fitBounds(bounds, { padding: 20 });
        })
        .catch((error) => {
            console.error('Error al cargar el mapa:', error);
            alert('Error al cargar las heladeras. Por favor, intente nuevamente.');
        });
});