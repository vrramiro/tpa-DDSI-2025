let map = L.map('map').setView([-33.3017, -66.3378], 5);
let markers = [];

// Cargar el mapa base
L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors &copy; <a href="https://carto.com/attributions">CARTO</a>',
    subdomains: 'abcd',
    maxZoom: 20
}).addTo(map);

// Función para limpiar marcadores
function limpiarMarcadores() {
    markers.forEach(marker => map.removeLayer(marker));
    markers = [];
}

// Función para renderizar hechos
function renderizarHechos(hechos) {
    limpiarMarcadores();

    if (!hechos) {
        console.warn("No hay hechos para renderizar.");
        return;
    }

    const MAX_LENGTH_DESCRIPCION = 75;

    hechos.forEach(hecho => {
        if (hecho.ubicacion && hecho.ubicacion.latitud != null && hecho.ubicacion.longitud != null) {

            let descripcionCorta;
            if (hecho.descripcion.length > MAX_LENGTH_DESCRIPCION) {
                descripcionCorta = hecho.descripcion.substring(0, MAX_LENGTH_DESCRIPCION) + '...';
            } else {
                descripcionCorta = hecho.descripcion;
            }

            const urlVerMas = `/hechos/${hecho.id}`;

            // --- Armado del Popup ---
            const popupContent = `
                <strong>${hecho.titulo}</strong><br>
                ${descripcionCorta}<br>
                ${hecho.fechaAcontecimiento} <br><br>
                <a href="${urlVerMas}">Ver Mas...</a>
            `;

            const marker = L.marker([hecho.ubicacion.latitud, hecho.ubicacion.longitud])
                .bindPopup(popupContent)
                .addTo(map);

            markers.push(marker);
        } else {
            console.warn("Hecho descartado por falta de 'ubicacion' o lat/lng:", hecho);
        }
    });
}

// Función para obtener los filtros
function obtenerFiltrosParaUrl() {
    const params = new URLSearchParams();

    // Ojo: los 'name' en el HTML son los que importan para la URL
    const fechaDesde = document.getElementById('fecha-desde').value;
    const fechaHasta = document.getElementById('fecha-hasta').value;
    const categoria = document.getElementById('categoria').value;
    const provincia = document.getElementById('provincia').value;
    const coleccion = document.getElementById('coleccion').value;

    // Usamos los 'name' del HTML que coinciden con los @RequestParam
    if (fechaDesde) params.append('fechaAcontecimientoDesde', fechaDesde);
    if (fechaHasta) params.append('fechaAcontecimientoHasta', fechaHasta);
    if (categoria) params.append('idCategoria', categoria);
    if (provincia) params.append('provincia', provincia);
    if (coleccion) params.append('idColeccion', coleccion);
    //TODO if (modoCurado) params.append('modoCurado', 'true');

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

    if (hasActiveFilter) {
        resetButton.style.display = 'block';
    } else {
        resetButton.style.display = 'none';
    }
}

filterButton.addEventListener('click', () => {
    const paramsString = obtenerFiltrosParaUrl();
    window.location.href = `/hechos/explorador?${paramsString}`;
});

resetButton.addEventListener('click', () => {
    window.location.href = '/hechos/explorador';
});

document.addEventListener('DOMContentLoaded', () => {
    console.log("Hechos recibidos del servidor:", initialHechos);
    renderizarHechos(initialHechos);
    toggleResetButtonVisibility();
});

filterInputs.forEach(input => {
    input.addEventListener('change', toggleResetButtonVisibility);
});