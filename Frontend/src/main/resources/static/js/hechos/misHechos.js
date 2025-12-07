

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

    // 2. Capturar elementos de la tarjeta clicada
    // Nota: Usamos selectores que coinciden con tu nuevo HTML
    const title = cardElement.querySelector('.card-title').innerText;

    // Intentamos buscar la fecha, si está dentro de un span o directa
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

    // Guardar para carrusel (preparado para futuro)
    imagenesActuales = [imgSrc];
    indiceImagenActual = 0;

    // 4. Rellenar el Modal
    document.getElementById('modal-title').innerText = title;
    document.getElementById('modal-date').innerText = date;
    document.getElementById('modal-image').src = imgSrc;
    document.getElementById('modal-category').innerText = category;

    // Aquí podrías concatenar texto si la descripción en la tarjeta está recortada
    document.getElementById('modal-description').innerText = fullDescription;

    // 5. Actualizar botones de acción
    actualizarEnlacesModal(hechoActualId);

    // 6. MOSTRAR MODAL (Usando la clase CSS .active)
    const modalOverlay = document.getElementById('eventModal');
    modalOverlay.classList.add('active');
    document.body.style.overflow = 'hidden'; // Bloquear scroll del fondo
}

/* ----------------------------------------------------------------
   Cerrar Modal
   ---------------------------------------------------------------- */
function cerrarModal() {
    const modalOverlay = document.getElementById('eventModal');
    modalOverlay.classList.remove('active'); // Ocultar quitando la clase
    document.body.style.overflow = 'auto'; // Restaurar scroll

    // Limpiar variables
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

    if (verMasBtn) {
        // Ajusta la URL según tu controlador Spring Boot
        verMasBtn.href = `/hechos/${hechoId}`;    }

    if (editarBtn) {
        // Ajusta la URL según tu controlador Spring Boot
        editarBtn.href = `/hechos/${hechoId}/editar`;
    }
}

/* ----------------------------------------------------------------
   Eliminar Hecho (Lógica JS)
   ---------------------------------------------------------------- */
function eliminarHecho() {
    if (!hechoActualId) return;

    if (confirm('¿Estás seguro de que deseas eliminar este hecho? Esta acción no se puede deshacer.')) {
        // Redireccionar al endpoint de eliminación
        // Asegúrate que tu Controller maneje GET para eliminar o usa fetch con DELETE
        window.location.href = `/hechos/eliminar/${hechoActualId}`;
    }
}

/* ----------------------------------------------------------------
   Carrusel (Opcional - Si tienes múltiples imágenes)
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
    // Pequeño efecto de parpadeo
    modalImage.style.opacity = '0.5';
    setTimeout(() => {
        modalImage.src = imagenesActuales[indiceImagenActual];
        modalImage.style.opacity = '1';
    }, 150);
}