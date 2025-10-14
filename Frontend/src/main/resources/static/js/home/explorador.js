let map = L.map('map').setView([-33.3017, -66.3378], 5);
let markers = [];

// Cargar el mapa base
L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors &copy; <a href="https://carto.com/attributions">CARTO</a>',
    subdomains: 'abcd',
    maxZoom: 20
}).addTo(map);

// Función para limpiar marcadores actuales
function limpiarMarcadores() {
    markers.forEach(marker => map.removeLayer(marker));
    markers = [];
}

// Función para renderizar hechos en el mapa
function renderizarHechos(hechos) {
    limpiarMarcadores();

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

// Función para obtener solo los filtros que tienen valor
function obtenerFiltrosActivos() {
    const params = new URLSearchParams();

    const fechaDesde = document.getElementById('fecha-desde').value;
    const fechaHasta = document.getElementById('fecha-hasta').value;
    const categoria = document.getElementById('categoria').value;
    const ubicacion = document.getElementById('ubicacion').value;
    const coleccion = document.getElementById('coleccion').value;

    if (fechaDesde) params.append('fechaDesde', fechaDesde);
    if (fechaHasta) params.append('fechaHasta', fechaHasta);
    if (categoria) params.append('categoria', categoria);
    if (ubicacion) params.append('ubicacion', ubicacion);
    if (coleccion) params.append('coleccion', coleccion);

    return params;
}

// Cargar hechos con filtros activos
function cargarHechos() {
    const params = obtenerFiltrosActivos();

    fetch(`/api/hechos/filtrar?${params}`, { method: 'POST' })
        .then(response => response.json())
        .then(hechos => renderizarHechos(hechos))
        .catch(error => console.error('Error al cargar hechos:', error));
}

// Event listener para el botón de filtrar
document.getElementById('btn-filtrar').addEventListener('click', cargarHechos);

// Cargar hechos al iniciar la página
cargarHechos();