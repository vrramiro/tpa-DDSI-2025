(function () {
    function dismissFlashCreated() {
        var el = document.getElementById('flash-created');
        if (el) el.remove();
        try { sessionStorage.setItem('flash-created-dismissed', '1'); } catch (_) {}
    }
    window.dismissFlashCreated = dismissFlashCreated;

    window.addEventListener('DOMContentLoaded', function () {
        var wrap = document.getElementById('flash-created');
        if (!wrap) return;

        wrap.scrollIntoView({ behavior: 'smooth', block: 'start' });

        // auto-ocultar a los 6s (opcional)
        setTimeout(dismissFlashCreated, 6000);
    });

    // Si volvés con “Atrás” (bfcache), no lo muestres otra vez
    window.addEventListener('pageshow', function (e) {
        var cameFromHistory = e.persisted || (performance.getEntriesByType('navigation')[0] && performance.getEntriesByType('navigation')[0].type === 'back_forward');
        if (cameFromHistory) {
            try {
                if (sessionStorage.getItem('flash-created-dismissed') === '1') {
                    var el = document.getElementById('flash-created');
                    if (el) el.remove();
                    sessionStorage.removeItem('flash-created-dismissed');
                }
            } catch (_) {}
        }
    });
})();