document.addEventListener('DOMContentLoaded', function() {

    const mapElement = document.getElementById('map-detail');

    if (mapElement) {
        const lat = parseFloat(mapElement.dataset.lat);
        const lng = parseFloat(mapElement.dataset.lng);
        const titulo = mapElement.dataset.titulo || 'Hecho';

        if (!isNaN(lat) && !isNaN(lng)) {

            // Lógica de Leaflet
            const map = L.map('map-detail').setView([lat, lng], 14);

            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '© OpenStreetMap contributors'
            }).addTo(map);

            L.marker([lat, lng]).addTo(map)
                .bindPopup(`<b>${titulo}</b><br>Ubicación exacta.`)
                .openPopup();

        } else {
            mapElement.innerHTML =
                '<p style="text-align:center; padding:2rem; color:#666;">Ubicación no disponible.</p>';
        }
    }
});