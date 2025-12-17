document.addEventListener("DOMContentLoaded", function() {

    // --- LÓGICA DE CARGA (SUBMIT) ---
    const formImportar = document.getElementById("form-importar");
    const loadingMessage = document.getElementById("loading-message");
    const serverMessage = document.getElementById("server-message");
    const btnSubir = document.getElementById("btn-subir");
    const fileInput = document.getElementById("archivo");

    if (formImportar) {
        formImportar.addEventListener("submit", function (event) {
            if (!fileInput.value) {
                event.preventDefault();
                alert("Seleccione un archivo primero.");
                return;
            }

            // 1. Mostrar mensaje de carga (Azul)
            if (loadingMessage) loadingMessage.classList.remove("hidden");

            // 2. Ocultar mensaje anterior del servidor si existe
            if (serverMessage) serverMessage.style.display = 'none';

            // 3. Bloquear botón
            if (btnSubir) {
                btnSubir.disabled = true;
                btnSubir.innerText = "Subiendo...";
            }
        });
    }

    // --- LÓGICA DE DESAPARICIÓN (TIMER 3 SEGUNDOS) ---
    if (serverMessage) {
        // Esperar 3000ms (3 segundos)
        setTimeout(function() {
            // Agregar clase para animar la opacidad
            serverMessage.classList.add("fade-out");

            // Esperar a que termine la transición (0.5s) y eliminar del DOM
            setTimeout(function() {
                serverMessage.remove();
            }, 500);
        }, 3000);
    }
});