// Inicialización del mapa
let map;
let marker;

// Coordenadas por defecto (Buenos Aires, Argentina)
const DEFAULT_LAT = -34.6037;
const DEFAULT_LNG = -58.3816;

// Inicializar el mapa cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', function() {
    initMap();
    initModal();
});

// Inicializar el mapa de Leaflet
function initMap() {
    // Obtener las coordenadas actuales del hecho (si existen)
    const latInput = document.getElementById('latitud');
    const lngInput = document.getElementById('longitud');

    const currentLat = latInput.value ? parseFloat(latInput.value) : DEFAULT_LAT;
    const currentLng = lngInput.value ? parseFloat(lngInput.value) : DEFAULT_LNG;

    // Crear el mapa centrado en las coordenadas actuales o por defecto
    map = L.map('map').setView([currentLat, currentLng], 13);

    // Agregar capa de tiles de OpenStreetMap
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors',
        maxZoom: 19
    }).addTo(map);

    // Crear marcador en la posición actual o por defecto
    marker = L.marker([currentLat, currentLng], {
        draggable: true
    }).addTo(map);

    // Actualizar inputs cuando se mueve el marcador
    marker.on('dragend', function(e) {
        const position = marker.getLatLng();
        updateLocationInputs(position.lat, position.lng);
    });

    // Permitir click en el mapa para mover el marcador
    map.on('click', function(e) {
        marker.setLatLng(e.latlng);
        updateLocationInputs(e.latlng.lat, e.latlng.lng);
    });
}

// Actualizar los inputs ocultos de latitud y longitud
function updateLocationInputs(lat, lng) {
    document.getElementById('latitud').value = lat.toFixed(6);
    document.getElementById('longitud').value = lng.toFixed(6);
}

// Inicializar y manejar el modal
function initModal() {
    const modal = document.getElementById('mensajeModal');

    // Si el modal existe, mostrarlo automáticamente
    if (modal) {
        modal.style.display = 'flex';

        // Cerrar modal al hacer click fuera del contenido
        modal.addEventListener('click', function(e) {
            if (e.target === modal) {
                cerrarModal();
            }
        });

        // Cerrar modal con tecla Escape
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Escape' && modal.style.display === 'flex') {
                cerrarModal();
            }
        });
    }
}

// Función para cerrar el modal
function cerrarModal() {
    const modal = document.getElementById('mensajeModal');
    const debeRedirigir = modal.getAttribute('data-redirect') === 'true';

    if (debeRedirigir) {
        // Si fue exitoso, redirigir a mis hechos
        window.location.href = '/hechos/misHechos';
    } else {
        // Si fue error, solo cerrar el modal
        modal.style.display = 'none';
    }
}