document.addEventListener('DOMContentLoaded', () => {
    // Busca el nodo oculto con los datos del flash
    const flash = document.getElementById('flash-data');
    if (!flash) return;

    const mensaje = flash.dataset.mensaje?.trim();
    const tipo = flash.dataset.tipo || 'info';

    console.log('[FLASH DEBUG]', { mensaje, tipo }); // Verificá en consola

    // Si no hay mensaje, no muestra nada
    if (!mensaje) return;

    // Crear contenedor del toast
    const toast = document.createElement('div');
    toast.className = `toast-notify ${tipo}`;
    toast.innerHTML = `
    <i class="fas ${
        tipo === 'success' ? 'fa-check-circle' :
            tipo === 'error' ? 'fa-times-circle' :
                'fa-info-circle'
    }"></i>
    <span>${mensaje}</span>
    <button class="close-btn" aria-label="Cerrar">&times;</button>
  `;

    document.body.appendChild(toast);

    // Animación de entrada
    setTimeout(() => toast.classList.add('show'), 20);

    // Cierre manual o automático
    toast.querySelector('.close-btn').onclick = cerrar;
    setTimeout(cerrar, 4000);

    function cerrar() {
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 300);
    }
});