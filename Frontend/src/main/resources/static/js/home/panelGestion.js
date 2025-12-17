const menuToggle = document.getElementById("menu-toggle");
const mainNav = document.getElementById("main-nav");

if (menuToggle && mainNav) {
    menuToggle.addEventListener("click", () => {
        mainNav.classList.toggle("show");
    });
}

/* ===== LOGICA DEL LOADER DE IMPORTACIÓN ===== */
const formImportar = document.getElementById("form-importar");
const loaderOverlay = document.getElementById("loader-overlay");

if (formImportar && loaderOverlay) {
    formImportar.addEventListener("submit", function (event) {
        //  si el formulario es válido (HTML5 validation)

        loaderOverlay.classList.add("loader-visible");

        const btnSubmit = formImportar.querySelector("button[type='submit']");
        if(btnSubmit) btnSubmit.disabled = true;
    });
}