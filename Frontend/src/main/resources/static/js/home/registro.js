const menuToggle = document.getElementById("menu-toggle");
const mainNav = document.getElementById("main-nav");

menuToggle.addEventListener("click", () => {
    mainNav.classList.toggle("show");
});

window.onload = function() {
    const modal = document.getElementById('mensajeModal');
    if (modal) {
        modal.style.display = 'flex';
        modal.addEventListener('click', function(e) {
            if (e.target === modal) cerrarModal();
        });
    }
}

function cerrarModal() {
    const modal = document.getElementById('mensajeModal');
    if (modal) modal.style.display = 'none';
}

