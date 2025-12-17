let imagenesActuales = [];
let indiceImagenActual = 0;
let hechoActualId = null;

document.addEventListener('DOMContentLoaded', () => {
    const modalOverlay = document.getElementById('eventModal');

    if (modalOverlay) {
        modalOverlay.addEventListener('click', (event) => {
            if (event.target === modalOverlay) {
                cerrarModal();
            }
        });
    }

    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') {
            const modal = document.getElementById('eventModal');
            if (modal && modal.classList.contains('active')) {
                cerrarModal();
            }
        }
    });
});


function abrirModalHecho(cardElement) {
    // 1. Capturar ID
    hechoActualId = cardElement.getAttribute('data-id');

    const tipoFuente = cardElement.getAttribute('data-tipo-fuente');

    // 2. Capturar elementos de la tarjeta clicada
    const title = cardElement.querySelector('.card-title').innerText;

    // Intentamos buscar la fecha
    const dateEl = cardElement.querySelector('.card-date');
    const date = dateEl ? dateEl.innerText : '';

    const categoryEl = cardElement.querySelector('.card-badge');
    const category = categoryEl ? categoryEl.innerText : 'Sin categoría';

    const descriptionEl = cardElement.querySelector('.card-description');
    const fullDescription = descriptionEl ? descriptionEl.innerText : '';

    // 3. Manejo de la Imagen
    const imgElement = cardElement.querySelector('img');
    let imgSrc = 'https://placehold.co/800x400?text=Sin+Imagen'; // Default

    // Si la imagen existe y no es el placeholder de error
    if (imgElement && imgElement.src) {
        imgSrc = imgElement.src;
    }

    // Guardar para carrusel
    imagenesActuales = [imgSrc];
    indiceImagenActual = 0;

    // 4. Rellenar el Modal
    document.getElementById('modal-title').innerText = title;
    document.getElementById('modal-date').innerText = date;
    document.getElementById('modal-image').src = imgSrc;
    document.getElementById('modal-category').innerText = category;
    document.getElementById('modal-description').innerText = fullDescription;

    // 5. Actualizar botones de acción
    actualizarEnlacesModal(hechoActualId);
    gestionarVisibilidadBotonEditar(tipoFuente);

    // 6. MOSTRAR MODAL
    const modalOverlay = document.getElementById('eventModal');
    modalOverlay.classList.add('active');
    document.body.style.overflow = 'hidden';

}

function gestionarVisibilidadBotonEditar(tipoFuente) {
    const editarBtn = document.getElementById('modal-editar');
    if (editarBtn) {
        if (tipoFuente === 'DINAMICA') {
            editarBtn.style.display = 'inline-block'; // O 'flex', según tu CSS base
        } else {
            editarBtn.style.display = 'none';
        }
    }
}

/* ----------------------------------------------------------------
   Cerrar Modal
   ---------------------------------------------------------------- */
function cerrarModal() {
    const modalOverlay = document.getElementById('eventModal');
    modalOverlay.classList.remove('active');
    document.body.style.overflow = 'auto';

    imagenesActuales = [];
    indiceImagenActual = 0;
    hechoActualId = null;
}

/* ----------------------------------------------------------------
   Actualizar Enlaces (Ver más / Editar)
   ---------------------------------------------------------------- */
function actualizarEnlacesModal(hechoId) {
    if (!hechoId) return;

    const verMasBtn = document.getElementById('modal-ver-mas');
    const editarBtn = document.getElementById('modal-editar');

    // Aseguramos que el botón eliminar tenga el evento onclick asignado
    // (Aunque ya lo pusimos en el HTML, es buena práctica reforzarlo o dejarlo limpio aquí)
    const eliminarBtn = document.getElementById('modal-eliminar');
    if (eliminarBtn) {
        eliminarBtn.onclick = eliminarHecho;
    }

    if (verMasBtn) {
        verMasBtn.href = `/hechos/${hechoId}`;
    }

    if (editarBtn) {
        editarBtn.href = `/hechos/${hechoId}/editar`;
    }
}

/* ----------------------------------------------------------------
   Eliminar Hecho (REDIRECCIÓN DIRECTA)
   ---------------------------------------------------------------- */
function eliminarHecho() {
    if (!hechoActualId) return;

    // CAMBIO: Se eliminó el "confirm" y la redirección es inmediata.
    window.location.href = `/solicitudes/crearSolicitud?hechoId=${hechoActualId}`;
}

/* ----------------------------------------------------------------
   Carrusel
   ---------------------------------------------------------------- */
function cambiarImagen(direccion) {
    if (imagenesActuales.length <= 1) return;

    indiceImagenActual += direccion;

    if (indiceImagenActual < 0) {
        indiceImagenActual = imagenesActuales.length - 1;
    } else if (indiceImagenActual >= imagenesActuales.length) {
        indiceImagenActual = 0;
    }

    const modalImage = document.getElementById('modal-image');
    modalImage.style.opacity = '0.5';
    setTimeout(() => {
        modalImage.src = imagenesActuales[indiceImagenActual];
        modalImage.style.opacity = '1';
    }, 150);
}