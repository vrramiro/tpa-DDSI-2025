let map;
let markers = [];

const iconoUnificado = L.icon({
    iconUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png',
    // TODO: ver......  shadowUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

if (centroLat && centroLng) {
    map = L.map('map').setView([centroLat, centroLng], 15);
} else {
    map = L.map('map').setView([-33.3017, -66.3378], 5);
}

L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png', {
    attribution: '&copy; OpenStreetMap contributors &copy; CARTO',
    subdomains: 'abcd',
    maxZoom: 20
}).addTo(map);

function limpiarMarcadores() {
    markers.forEach(marker => map.removeLayer(marker));
    markers = [];
}

function renderizarHechos(hechos) {
    limpiarMarcadores();

    if (!hechos) {
        console.warn("No hay hechos para renderizar.");
        return;
    }

    const MAX_LENGTH_DESCRIPCION = 75;

    hechos.forEach(hecho => {
        if (hecho.ubicacion && hecho.ubicacion.latitud != null && hecho.ubicacion.longitud != null) {

            let descripcionCorta = hecho.descripcion.length > MAX_LENGTH_DESCRIPCION
                ? hecho.descripcion.substring(0, MAX_LENGTH_DESCRIPCION) + '...'
                : hecho.descripcion;

            const urlVerMas = `/hechos/${hecho.id}`;
            const fechaFormateada = hecho.fechaAcontecimiento
                ? hecho.fechaAcontecimiento.split('-').reverse().join('/')
                : '';

            const popupContent = `
                <strong>${hecho.titulo}</strong><br>
                ${descripcionCorta}<br>
                ${fechaFormateada} <br><br>
                <a href="${urlVerMas}">Ver Mas...</a>
            `;

            const marker = L.marker([hecho.ubicacion.latitud, hecho.ubicacion.longitud], {
                icon: iconoUnificado // <--- ESTO FUERZA QUE SEAN IGUALES
            })
                .bindPopup(popupContent)
                .addTo(map);

            markers.push(marker);
        }
    });
}

function obtenerFiltrosParaUrl() {
    const params = new URLSearchParams();
    const fechaDesde = document.getElementById('fecha-desde').value;
    const fechaHasta = document.getElementById('fecha-hasta').value;
    const categoria = document.getElementById('categoria').value;
    const provincia = document.getElementById('provincia').value;
    const coleccion = document.getElementById('coleccion').value;

    if (fechaDesde) params.append('fechaAcontecimientoDesde', fechaDesde);
    if (fechaHasta) params.append('fechaAcontecimientoHasta', fechaHasta);
    if (categoria) params.append('idCategoria', categoria);
    if (provincia) params.append('provincia', provincia);
    if (coleccion) params.append('idColeccion', coleccion);

    return params.toString();
}

const filterButton = document.getElementById('btn-filtrar');
const resetButton = document.getElementById('btn-resetear');
const filterInputs = document.querySelectorAll('aside.filters .input');

function toggleResetButtonVisibility() {
    let hasActiveFilter = false;
    for (const input of filterInputs) {
        if (input.value !== '') {
            hasActiveFilter = true;
            break;
        }
    }
    resetButton.style.display = hasActiveFilter ? 'block' : 'none';
}

filterButton.addEventListener('click', () => {
    window.location.href = `/hechos/explorador?${obtenerFiltrosParaUrl()}`;
});

resetButton.addEventListener('click', () => {
    window.location.href = '/hechos/explorador';
});

document.addEventListener('DOMContentLoaded', () => {
    console.log("Hechos recibidos:", initialHechos);
    renderizarHechos(initialHechos);
    toggleResetButtonVisibility();
});

filterInputs.forEach(input => {
    input.addEventListener('change', toggleResetButtonVisibility);
});