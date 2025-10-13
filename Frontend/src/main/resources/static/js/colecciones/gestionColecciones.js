// Estado temporal del modal
let _coleccionAEliminar = { id: null, titulo: '' };

// Abre el modal y carga los datos desde el botón
function abrirModalEliminarColeccion(btn) {
    const id = btn.dataset.id;
    const titulo = btn.dataset.titulo || 'esta colección';

    _coleccionAEliminar = { id, titulo };

    const modal = document.getElementById('modal-eliminar');
    const texto = document.getElementById('modal-eliminar-texto');
    texto.textContent = `¿Seguro que querés eliminar «${titulo}»? Esta acción no se puede deshacer.`;

    modal.hidden = false;

    // Enfoque accesible al botón confirmar
    setTimeout(() => document.getElementById('modal-eliminar-confirmar').focus(), 0);
}

// Cierra el modal
function cerrarModalEliminarColeccion() {
    const modal = document.getElementById('modal-eliminar');
    modal.hidden = true;
    _coleccionAEliminar = { id: null, titulo: '' };
}

// Click en "Cancelar"
document.getElementById('modal-eliminar-cancelar')?.addEventListener('click', cerrarModalEliminarColeccion);

// Click en "Confirmar" => envía POST con el form oculto
document.getElementById('modal-eliminar-confirmar')?.addEventListener('click', () => {
    if (!_coleccionAEliminar.id) return;

    const form = document.getElementById('form-eliminar-coleccion');
    form.action = `/colecciones/${_coleccionAEliminar.id}/eliminar`; // ruta POST
    form.submit(); // dispara el POST al backend

    cerrarModalEliminarColeccion();
});

// Cerrar con click fuera del contenido
document.getElementById('modal-eliminar')?.addEventListener('click', (e) => {
    if (e.target.id === 'modal-eliminar') cerrarModalEliminarColeccion();
});

// Cerrar con ESC
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && !document.getElementById('modal-eliminar').hidden) {
        cerrarModalEliminarColeccion();
    }
});
