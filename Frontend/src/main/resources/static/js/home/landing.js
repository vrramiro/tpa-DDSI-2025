
const menuToggle = document.getElementById("menu-toggle");
const mainNav = document.getElementById("main-nav");

if (menuToggle && mainNav) {
    menuToggle.addEventListener("click", () => {
        mainNav.classList.toggle("show");
    });
}

const API_AGREGADOR = (typeof API_AGREGADOR_CONFIG !== 'undefined')
    ? API_AGREGADOR_CONFIG
    : 'http://localhost:8082';

document.addEventListener("DOMContentLoaded", () => {
    cargarHechosDestacados().catch(error => console.error(error));
});

async function cargarHechosDestacados() {
    const contenedor = document.getElementById('card-container-hechos');

    if (!contenedor) return; // Evita errores si no encuentra el div

    try {
        const response = await fetch(`${API_AGREGADOR}/hechos/recientes?page=0&size=3&sort=fechaAcontecimiento,desc`);

        if (!response.ok) throw new Error('Error al obtener hechos');

        const data = await response.json();
        const hechos = data.content || [];

        contenedor.innerHTML = '';

        if (hechos.length === 0) {
            contenedor.innerHTML = '<div class="col-12 text-center"><p class="text-muted">No hay hechos recientes.</p></div>';
            return;
        }

        hechos.forEach(hecho => {
            const card = `
                <div class="col-md-4 mb-4">
                    <div class="card h-100 shadow-sm border-0 hover-effect">
                        <div class="card-body">
                            <div class="mb-2">
                                <span class="badge bg-light text-dark border">${hecho.categoria || 'Hecho'}</span>
                            </div>
                            
                            <h5 class="card-title fw-bold text-dark mb-3">${hecho.titulo}</h5>
                            
                            <p class="card-text text-muted">
                                ${hecho.descripcion
                ? (hecho.descripcion.length > 120 ? hecho.descripcion.substring(0, 120) + '...' : hecho.descripcion)
                : 'Sin descripción disponible.'}
                            </p>
                        </div>
                        
                        <div class="card-footer bg-white border-top-0 pt-0 pb-3">
                            <div class="d-flex justify-content-between align-items-center">
                                <small class="text-muted">
                                    <i class="bi bi-calendar3 me-1"></i>
                                    ${new Date(hecho.fechaAcontecimiento).toLocaleDateString()}
                                </small>
                                <a href="/hechos/${hecho.id}" class="btn btn-sm btn-outline-primary rounded-pill px-3">Ver más</a>
                            </div>
                        </div>
                    </div>
                </div>
            `;
            contenedor.insertAdjacentHTML('beforeend', card);
        });

    } catch (error) {
        console.error("Error hechos:", error);
        contenedor.innerHTML = `<div class="col-12 text-center text-danger">No se pudieron cargar los hechos.</div>`;
    }
}