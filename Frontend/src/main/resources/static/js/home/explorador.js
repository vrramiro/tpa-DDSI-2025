let map;
let markers = [];
let abortController = null; // Para cancelar peticiones anteriores si se mueve rápido

const iconoUnificado = L.icon({
    iconUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

// --- INICIO CONFIGURACIÓN LIMITES ARGENTINA ---
// 1. Definimos los límites (Bounding Box)
const southWest = L.latLng(-56.5, -76.0); // Tierra del Fuego / Andes
const northEast = L.latLng(-21.0, -52.0); // Norte / Misiones
const bounds = L.latLngBounds(southWest, northEast);

// 2. Opciones del mapa con las restricciones
const mapOptions = {
    minZoom: 6,               // Evita que se alejen demasiado (vista global)
    maxBounds: bounds,        // Restringe la navegación a estos bordes
    maxBoundsViscosity: 1.0   // Rebote sólido al intentar salir
};

// 3. Inicialización del mapa
if (typeof centroLat !== 'undefined' && centroLat && typeof centroLng !== 'undefined' && centroLng) {
    // Si hay un hecho seleccionado (viene del backend), centramos ahí con zoom alto
    map = L.map('map', mapOptions).setView([centroLat, centroLng], 15);
} else {
    // CAMBIO APLICADO: Arranca en BUENOS AIRES (Obelisco) con Zoom 12
    map = L.map('map', mapOptions).setView([-34.6037, -58.3816], 12);
}
// --- FIN CONFIGURACIÓN LIMITES ---

L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png', {
    attribution: '&copy; OpenStreetMap contributors &copy; CARTO',
    subdomains: 'abcd',
    maxZoom: 20
}).addTo(map);

// Evento principal: cargar hechos al mover o hacer zoom en el mapa
map.on('moveend', cargarHechosEnVista);

function limpiarMarcadores() {
    markers.forEach(marker => map.removeLayer(marker));
    markers = [];
}

function cargarHechosEnVista() {
    const bounds = map.getBounds();
    const params = new URLSearchParams(obtenerFiltrosParaUrl()); // Filtros del formulario

    // Agregamos coordenadas del Viewport actual para que el backend filtre
    params.append('latMin', bounds.getSouth());
    params.append('latMax', bounds.getNorth());
    params.append('lonMin', bounds.getWest());
    params.append('lonMax', bounds.getEast());

    // Cancelar petición anterior si el usuario sigue moviendo el mapa
    if (abortController) abortController.abort();
    abortController = new AbortController();

    fetch(`/hechos/api/data?${params.toString()}`, { signal: abortController.signal })
        .then(response => {
            if (!response.ok) throw new Error("Error en la respuesta del servidor");
            return response.json();
        })
        .then(data => renderizarHechos(data))
        .catch(err => {
            if (err.name !== 'AbortError') console.error('Error cargando hechos:', err);
        });
}

function renderizarHechos(hechos) {
    limpiarMarcadores();

    if (!hechos || hechos.length === 0) return;

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
            <div style="text-align: center;">
            <strong style="font-size: 14px;">${hecho.titulo}</strong><br>
            <span style="font-size: 13px; color: #555;">${descripcionCorta}</span><br>
            <small style="color: #888;">${fechaFormateada}</small> <br>
            <a href="${urlVerMas}" class="btn-ver-mas">Ver detalle</a>
             </div>
            `;

            const marker = L.marker([hecho.ubicacion.latitud, hecho.ubicacion.longitud], {
                icon: iconoUnificado
            }).bindPopup(popupContent).addTo(map);

            markers.push(marker);
        }
    });
}

function obtenerFiltrosParaUrl() {
    const params = new URLSearchParams();

    const inputFechaDesde = document.getElementById('fecha-desde');
    const inputFechaHasta = document.getElementById('fecha-hasta');
    const inputCategoria = document.getElementById('categoria');
    const inputProvincia = document.getElementById('provincia');

    // Recuperamos el input del modo curado
    const inputModoCurado = document.getElementById('modo-curado');
    // Si tuvieras input de colección, descomentar: const inputColeccion = document.getElementById('coleccion');

    if (inputFechaDesde && inputFechaDesde.value) params.append('fechaAcontecimientoDesde', inputFechaDesde.value);
    if (inputFechaHasta && inputFechaHasta.value) params.append('fechaAcontecimientoHasta', inputFechaHasta.value);
    if (inputCategoria && inputCategoria.value) params.append('idCategoria', inputCategoria.value);
    if (inputProvincia && inputProvincia.value) params.append('provincia', inputProvincia.value);

    // Lógica para modo curado
    if (inputModoCurado && inputModoCurado.checked) {
        params.append('modoCurado', 'true');
    } else {
        params.append('modoCurado', 'false');
    }

    // if (inputColeccion && inputColeccion.value) params.append('idColeccion', inputColeccion.value);

    return params;
}

// Botones de filtro
const filterButton = document.getElementById('btn-filtrar');
const resetButton = document.getElementById('btn-resetear');
// Agregamos el checkbox a los listeners para que aparezca el boton resetear
const filterInputs = document.querySelectorAll('aside.filters .input, aside.filters input[type="checkbox"]');

if (filterButton) {
    filterButton.addEventListener('click', () => {
        // Al filtrar, recargamos la página para limpiar la URL y estado
        window.location.href = `/hechos/explorador?${obtenerFiltrosParaUrl().toString()}`;
    });
}

if (resetButton) {
    resetButton.addEventListener('click', () => {
        window.location.href = '/hechos/explorador';
    });
}

// Carga inicial
document.addEventListener('DOMContentLoaded', () => {
    cargarHechosEnVista(); // Primera carga
    toggleResetButtonVisibility();
});

function toggleResetButtonVisibility() {
    if (!resetButton) return;

    let hasActiveFilter = false;
    for (const input of filterInputs) {
        if (input.type === 'checkbox') {
            if (input.checked) hasActiveFilter = true;
        } else {
            if (input.value !== '') hasActiveFilter = true;
        }
        if (hasActiveFilter) break;
    }
    resetButton.style.display = hasActiveFilter ? 'block' : 'none';
}

filterInputs.forEach(input => {
    input.addEventListener('change', toggleResetButtonVisibility);
});