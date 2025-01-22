document.addEventListener('DOMContentLoaded', async function () {
    mapboxgl.accessToken = 'pk.eyJ1IjoiZ3J1cG8tMjItZGRzIiwiYSI6ImNtNHVmMnNlczBsaDAya29wNW91M2gyZzQifQ.5zb0wvUU8_k9rZfgg0yxXA';
    const map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/mapbox/streets-v11',
        center: [-58.4370894, -34.6075682], // Coordenadas de Buenos Aires
        zoom: 13,
    });

    try {
        const response = await fetch('/json/heladeras.json');
        if (!response.ok) {
            throw new Error(`Error al cargar las heladeras: ${response.statusText}`);
        }
        const geojsonData = await response.json();

        if (!geojsonData.features || !Array.isArray(geojsonData.features)) {
            throw new Error('El archivo GeoJSON no contiene el formato esperado.');
        }

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
                    "circle-stroke-width": 1,
                    "circle-opacity": 1,
                }
            });

            map.addLayer({
                id: "heladeras-hover",
                type: "circle",
                source: "heladeras",
                paint: {
                    "circle-color": "hsl(45, 85%, 65%)",
                    "circle-radius": 12,
                    "circle-stroke-color": "hsl(0, 0%, 0%)",
                    "circle-stroke-width": 2,
                    "circle-opacity": 0.7,
                },
                filter: ["==", ["get", "id"], ""],
            });

            const popup = new mapboxgl.Popup({
                closeButton: false,
                closeOnClick: false,
            });

            map.on('mouseenter', 'heladeras', (e) => {
                map.getCanvas().style.cursor = 'pointer';

                const {coordinates} = e.features[0].geometry;
                const {name, isActive} = e.features[0].properties;

                const color = isActive === 1 ? "hsl(120, 45%, 58%)" : "hsl(0, 55%, 54%)";

                popup
                    .setLngLat(coordinates)
                    .setHTML(
                        `<strong style="color: ${color};">${name}</strong>`
                    )
                    .addTo(map);

                map.setFilter('heladeras-hover', ['==', ['get', 'id'], e.features[0].properties.id]);
            });

            map.on('mouseleave', 'heladeras', () => {
                map.getCanvas().style.cursor = '';
                popup.remove();
                map.setFilter('heladeras-hover', ['==', ['get', 'id'], '']);
            });

            const bounds = geojsonData.features.reduce((bounds, feature) => {
                return bounds.extend(feature.geometry.coordinates);
            }, new mapboxgl.LngLatBounds());
            map.fitBounds(bounds, {padding: 20});
        });

        map.on('click', 'heladeras', (e) => {
            const { id } = e.features[0].properties;

            window.location.href = `/heladeras/${id}`;
        });

    } catch (error) {
        console.error('Error al cargar el mapa:', error);
        alert('Error al cargar las heladeras. Por favor, intente nuevamente.');
    }
});
