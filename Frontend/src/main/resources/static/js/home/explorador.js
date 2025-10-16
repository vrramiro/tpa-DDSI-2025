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

    hechos.forEach(hecho => {
        if (hecho.latitud && hecho.longitud) {
            const marker = L.marker([hecho.latitud, hecho.longitud])
                .bindPopup(`
                    <strong>${hecho.titulo}</strong><br>
                    ${hecho.descripcion}<br>
                    <small>${hecho.fecha}</small>
                `)
                .addTo(map);

            markers.push(marker);
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

    return params.toString();
}

// --- LÓGICA PRINCIPAL ---

// 1. Event listener para el botón de filtrar
document.getElementById('btn-filtrar').addEventListener('click', () => {
    const paramsString = obtenerFiltrosParaUrl();

    // Recargamos la página con los nuevos filtros como query params.
    window.location.href = `/hechos/explorador?${paramsString}`;
});

// 2. Cargar hechos al iniciar la página
document.addEventListener('DOMContentLoaded', () => {
    renderizarHechos(initialHechos);
});