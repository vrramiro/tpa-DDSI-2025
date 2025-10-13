function filtrarPorEstado(estadoSeleccionado) {
    const urlParams = new URLSearchParams(window.location.search);

    if (estadoSeleccionado && estadoSeleccionado !== '') {
        urlParams.set('estado', estadoSeleccionado);
    } else {
        urlParams.delete('estado');
    }

    // Resetear la página a 0 cuando se cambia el filtro
    urlParams.set('page', '0');

    window.location.search = urlParams.toString();
}

function limpiarFiltro() {
    const urlParams = new URLSearchParams(window.location.search);

    // Eliminar el parámetro de estado
    urlParams.delete('estado');

    // Resetear la página a 0
    urlParams.set('page', '0');

    // Redirigir sin el parámetro de estado
    window.location.search = urlParams.toString();
}