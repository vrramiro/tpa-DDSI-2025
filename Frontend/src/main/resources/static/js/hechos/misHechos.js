let imagenesActuales = [];
let indiceImagenActual = 0;
let hechoActualId = null; // NUEVO: Variable para guardar el ID del hecho actual

document.addEventListener('DOMContentLoaded', () => {
    const cards = document.querySelectorAll('.card');
    const modalOverlay = document.getElementById('eventModal');
    const leftArrow = document.querySelector('.carousel-arrow.left');
    const rightArrow = document.querySelector('.carousel-arrow.right');

    // --- Lógica del Modal ---
    cards.forEach(card => {
        card.addEventListener('click', () => {
            // NUEVO: Capturar el ID del hecho desde data-id
            hechoActualId = card.getAttribute('data-id');

            const title = card.querySelector('.card-title').textContent;
            const date = card.querySelector('.card-date').textContent;
            const imgSrc = card.querySelector('.card-img').src;
            const category = card.querySelector('.footer-tag').textContent;
            // Toma toda la descripción completa
            const fullDescription = card.querySelector('.card-description').textContent;

            // Guardar imágenes para el carrusel (por ahora solo una, puedes extender)
            imagenesActuales = [imgSrc];
            indiceImagenActual = 0;

            document.getElementById('modal-title').textContent = title;
            document.getElementById('modal-date').textContent = date;
            document.getElementById('modal-image').src = imgSrc;
            document.getElementById('modal-category').textContent = category;
            document.getElementById('modal-description').textContent = fullDescription;

            // NUEVO: Actualizar los enlaces del modal con el ID correcto
            actualizarEnlacesModal(hechoActualId);

            // Mostrar/ocultar flechas del carrusel
            actualizarVisibilidadFlechas();

            modalOverlay.style.display = 'flex';
            document.body.style.overflow = 'hidden'; // Prevenir scroll
        });
    });

    // Cerrar modal al hacer clic fuera de él
    modalOverlay.addEventListener('click', (event) => {
        if (event.target === modalOverlay) {
            cerrarModal();
        }
    });

    // Cerrar modal con tecla ESC
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && modalOverlay.style.display === 'flex') {
            cerrarModal();
        }
    });

    // --- Carrusel de Imágenes ---
    if (leftArrow) {
        leftArrow.addEventListener('click', (e) => {
            e.stopPropagation();
            cambiarImagen(-1);
        });
    }

    if (rightArrow) {
        rightArrow.addEventListener('click', (e) => {
            e.stopPropagation();
            cambiarImagen(1);
        });
    }
});

// NUEVA FUNCIÓN: Actualizar los enlaces del modal
function actualizarEnlacesModal(hechoId) {
    if (!hechoId) return;

    const verMasBtn = document.getElementById('modal-ver-mas');
    const editarBtn = document.getElementById('modal-editar');

    if (verMasBtn) {
        verMasBtn.href = `/hechos/${hechoId}`;
    }

    if (editarBtn) {
        editarBtn.href = `/hechos/${hechoId}/editar`;
    }
}

function cerrarModal() {
    const modalOverlay = document.getElementById('eventModal');
    modalOverlay.style.display = 'none';
    document.body.style.overflow = ''; // Restaurar scroll
    imagenesActuales = [];
    indiceImagenActual = 0;
    hechoActualId = null; // NUEVO: Limpiar el ID al cerrar
}

function cambiarImagen(direccion) {
    if (imagenesActuales.length <= 1) return;

    indiceImagenActual += direccion;

    // Wrap around
    if (indiceImagenActual < 0) {
        indiceImagenActual = imagenesActuales.length - 1;
    } else if (indiceImagenActual >= imagenesActuales.length) {
        indiceImagenActual = 0;
    }

    // Actualizar imagen con animación suave
    const modalImage = document.getElementById('modal-image');
    modalImage.style.opacity = '0.5';

    setTimeout(() => {
        modalImage.src = imagenesActuales[indiceImagenActual];
        modalImage.style.opacity = '1';
    }, 150);
}

function actualizarVisibilidadFlechas() {
    const leftArrow = document.querySelector('.carousel-arrow.left');
    const rightArrow = document.querySelector('.carousel-arrow.right');

    if (imagenesActuales.length <= 1) {
        if (leftArrow) leftArrow.style.display = 'none';
        if (rightArrow) rightArrow.style.display = 'none';
    } else {
        if (leftArrow) leftArrow.style.display = 'block';
        if (rightArrow) rightArrow.style.display = 'block';
    }
}
