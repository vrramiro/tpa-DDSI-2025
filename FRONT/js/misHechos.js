document.addEventListener('DOMContentLoaded', () => {
    const cards = document.querySelectorAll('.card');
    const modalOverlay = document.getElementById('eventModal');
    
    // Elementos del menú hamburguesa
    const menuToggle = document.getElementById('menu-toggle');
    const mainNav = document.getElementById('main-nav');

    // --- Lógica del Modal ---
    cards.forEach(card => {
        card.addEventListener('click', () => {
            const title = card.querySelector('.card-title').textContent;
            const date = card.querySelector('.card-date').textContent;
            const imgSrc = card.querySelector('.card-img').src;
            const category = card.querySelector('.footer-tag').textContent;
            // Corregido para tomar toda la descripción, no solo la visible
            const fullDescription = card.querySelector('.card-description').textContent;

            document.getElementById('modal-title').textContent = title;
            document.getElementById('modal-date').textContent = date;
            document.getElementById('modal-image').src = imgSrc;
            document.getElementById('modal-category').textContent = category;
            document.getElementById('modal-description').textContent = fullDescription;

            modalOverlay.style.display = 'flex';
        });
    });

    modalOverlay.addEventListener('click', (event) => {
        if (event.target === modalOverlay) {
            modalOverlay.style.display = 'none';
        }
    });

    // --- Lógica del Menú Hamburguesa (CÓDIGO CORRECTO) ---
    if (menuToggle && mainNav) {
        menuToggle.addEventListener('click', () => {
            mainNav.classList.toggle('show');
        });
    }
});