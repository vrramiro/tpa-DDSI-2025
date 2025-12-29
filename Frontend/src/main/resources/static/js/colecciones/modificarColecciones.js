document.addEventListener('DOMContentLoaded', () => {

    /* =========================================================
       1. LÓGICA DEL MENÚ DE NAVEGACIÓN (Navbar Responsive)
       ========================================================= */
    const menuToggle = document.getElementById("menu-toggle");
    const mainNav = document.getElementById("main-nav");

    if (menuToggle && mainNav) {
        menuToggle.addEventListener("click", () => {
            mainNav.classList.toggle("show");
        });
    }

    /* =========================================================
       2. INICIALIZACIÓN DE TOM-SELECT (Selector de Provincias)
       ========================================================= */
    const $prov = document.getElementById('provincia');

    if ($prov) {
        // Helpers para búsqueda insensible a acentos
        const sinAcentos = (s) => s.normalize('NFD').replace(/\p{Diacritic}/gu, '');
        const esSubsecuencia = (texto, query) => {
            let i = 0, j = 0;
            while (i < texto.length && j < query.length) {
                if (texto[i] === query[j]) j++;
                i++;
            }
            return j === query.length;
        };

        new TomSelect('#provincia', {
            create: false,
            allowEmptyOption: true,
            persist: false,
            diacritics: true,
            searchField: ['text'],
            placeholder: 'Seleccione una provincia...',
            maxOptions: 24, // Las 24 jurisdicciones
            shouldSort: false,
            score: function (search) {
                const q = sinAcentos((search || '').trim().toLowerCase());
                if (!q.length) return () => 1;

                return function (item) {
                    const txt = sinAcentos((item.text || '').toLowerCase());
                    // Lógica de coincidencia
                    if (!esSubsecuencia(txt, q)) return 0;
                    if (txt.startsWith(q)) return 1;
                    const idx = txt.indexOf(q);
                    if (idx !== -1) return 0.8;
                    return 0.6;
                };
            }
        });
    }
});

/* =========================================================
   3. FUNCIONES GLOBALES DEL MODAL
   ========================================================= */

// Esta función es llamada por el botón "Entendido" / "Cerrar" del HTML
function cerrarModal() {
    const modal = document.getElementById('mensajeModal');
    if (modal) {
        modal.style.display = 'none';
    }
}

// Cerrar modal con la tecla ESC
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        cerrarModal();
    }
});