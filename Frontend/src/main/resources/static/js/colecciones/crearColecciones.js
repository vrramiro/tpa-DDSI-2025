document.addEventListener('DOMContentLoaded', () => {
    const $prov = document.getElementById('provincia');
    if (!$prov) return;

    // helpers: quitar acentos y chequear subsecuencia (mismo orden de letras)
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
        allowEmptyOption: true,       // permite valor vacío si querés “ninguna”
        persist: false,
        diacritics: true,             // respeta/normaliza acentos
        searchField: ['text'],
        placeholder: 'Provincia…',
        maxOptions: 24,
        shouldSort: false,            // mantiene el orden original de tus <option>
        score: function (search) {
            const q = sinAcentos((search || '').trim().toLowerCase());
            if (!q.length) return () => 1;

            return function (item) {
                const txt = sinAcentos((item.text || '').toLowerCase());

                // 1) si no respeta el orden de letras, no se muestra
                if (!esSubsecuencia(txt, q)) return 0;

                // 2) si empieza igual → “mejor” score
                if (txt.startsWith(q)) return 1;

                // 3) si contiene contiguo → score intermedio
                const idx = txt.indexOf(q);
                if (idx !== -1) return 0.8;

                // 4) sino, solo subsecuencia → score mínimo > 0
                return 0.6;
            };
        }
    });
});

function cerrarModal() {
    const modal = document.getElementById('mensajeModal');
    if (modal) {
        modal.style.display = 'none';
    }
}

document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        cerrarModal();
    }
});