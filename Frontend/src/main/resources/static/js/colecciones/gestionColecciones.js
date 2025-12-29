// Variable temporal para saber qué estamos borrando
let _coleccionAEliminar = { id: null, titulo: '' };

// 1. ABRIR MODAL
function abrirModalEliminarColeccion(btn) {
    const handle = btn.getAttribute('data-id');
    const titulo = btn.getAttribute('data-titulo') || 'esta colección';

    _coleccionAEliminar = { id: handle, titulo: titulo };

    const modal = document.getElementById('modal-eliminar');
    const texto = document.getElementById('modal-eliminar-texto');

    // Mensaje claro para el usuario
    texto.innerHTML = `Estás a punto de borrar definitivamente la colección <strong>"${titulo}"</strong>.<br><br>
                       Los hechos asociados NO se borrarán del sistema, pero la agrupación dejará de existir.`;

    // Mostrar el modal (quitamos hidden y forzamos flex)
    modal.hidden = false;
    modal.style.display = 'flex';
}

// 2. CERRAR MODAL
function cerrarModalEliminarColeccion() {
    const modal = document.getElementById('modal-eliminar');
    modal.hidden = true;
    modal.style.display = 'none';
    _coleccionAEliminar = { id: null, titulo: '' };
}

// Eventos de los botones del modal
document.addEventListener('DOMContentLoaded', () => {
    // Botón Cancelar
    const btnCancelar = document.getElementById('modal-eliminar-cancelar');
    if (btnCancelar) {
        btnCancelar.addEventListener('click', cerrarModalEliminarColeccion);
    }

    // Botón Confirmar (El "Sí, eliminar")
    const btnConfirmar = document.getElementById('modal-eliminar-confirmar');
    if (btnConfirmar) {
        btnConfirmar.addEventListener('click', () => {
            if (!_coleccionAEliminar.id) return;

            const form = document.getElementById('form-eliminar-coleccion');
            // Construimos la URL: /colecciones/{handle}/eliminar
            form.action = `/colecciones/${_coleccionAEliminar.id}/eliminar`;
            form.submit();
        });
    }

    // Cerrar si clic afuera
    const modal = document.getElementById('modal-eliminar');
    if (modal) {
        modal.addEventListener('click', (e) => {
            if (e.target === modal) cerrarModalEliminarColeccion();
        });
    }
});