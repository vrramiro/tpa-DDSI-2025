// Inicializar el mapa
let map = L.map('map').setView([-33.3017, -66.3378], 5);

L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors &copy; <a href="https://carto.com/attributions">CARTO</a>',
    subdomains: 'abcd',
    maxZoom: 20
}).addTo(map);

let marker = null;

function setMarker(lat, lng) {
    if (marker) {
        map.removeLayer(marker);
    }

    marker = L.marker([lat, lng]).addTo(map);
    map.setView([lat, lng], 13);

    document.getElementById('latitud').value = lat;
    document.getElementById('longitud').value = lng;
}

window.addEventListener('DOMContentLoaded', function() {
    const latInput = document.getElementById('latitud');
    const lngInput = document.getElementById('longitud');

    if (latInput.value && lngInput.value) {
        const lat = parseFloat(latInput.value);
        const lng = parseFloat(lngInput.value);

        if (!isNaN(lat) && !isNaN(lng)) {
            setMarker(lat, lng);
        }
    }
});

map.on('click', function(e) {
    setMarker(e.latlng.lat, e.latlng.lng);
    console.log('Coordenadas seleccionadas:', e.latlng.lat, e.latlng.lng);
});

function cerrarModal() {
    const modal = document.getElementById('mensajeModal');
    if (modal) {
        const debeRedirigir = modal.getAttribute('data-redirect') === 'true';
        modal.style.display = 'none';

        if (debeRedirigir) {
            window.location.href = window.location.origin + '/hechos/explorador';
        }
    }
}

window.onload = function() {
    const modal = document.getElementById('mensajeModal');
    if (modal) {
        modal.style.display = 'flex';

        modal.addEventListener('click', function(e) {
            if (e.target === modal) {
                cerrarModal();
            }
        });
    }
}

document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        cerrarModal();
    }
});