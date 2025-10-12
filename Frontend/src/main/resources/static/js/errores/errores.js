/* ===============================
   error404.js
   Interactividad para la página 404
   =============================== */

document.addEventListener("DOMContentLoaded", () => {
    const code = document.querySelector(".error-code");
    const title = document.querySelector(".error-title");

    // Pequeña animación de entrada
    if (code && title) {
        code.style.opacity = 0;
        title.style.opacity = 0;

        setTimeout(() => {
            code.style.transition = "opacity 0.8s ease, transform 0.8s ease";
            code.style.opacity = 1;
            code.style.transform = "scale(1.05)";
        }, 200);

        setTimeout(() => {
            title.style.transition = "opacity 0.8s ease";
            title.style.opacity = 1;
        }, 600);
    }

    // 🔄 Redirección automática opcional (comentar si no querés)
    const redirectAfter = 10; // segundos
    const redirectUrl = "/";  // destino (inicio)

    const message = document.querySelector(".error-message");
    if (message) {
        const countdown = document.createElement("span");
        countdown.style.fontWeight = "bold";
        countdown.textContent = ` Serás redirigido en ${redirectAfter}s...`;
        message.appendChild(countdown);

        let seconds = redirectAfter;
        const timer = setInterval(() => {
            seconds--;
            countdown.textContent = ` Serás redirigido en ${seconds}s...`;
            if (seconds <= 0) {
                clearInterval(timer);
                window.location.href = redirectUrl;
            }
        }, 1000);
    }

    // 💡 (Opcional) Registrar el error o evento en consola
    console.info("Página 404 mostrada. Recurso no encontrado.");
});
