document.addEventListener('DOMContentLoaded', () => {
    const cards = document.querySelectorAll('.card');
    const modalOverlay = document.getElementById('eventModal');

    // --- Lógica del Modal ---
    cards.forEach(card => {
        card.addEventListener('click', () => {
            const title = card.querySelector('.card-title').textContent;
            const date = card.querySelector('.card-date').textContent;
            const imgSrc = card.querySelector('.card-img').src;
            const category = card.querySelector('.footer-tag').textContent;
            // Toma toda la descripción completa
            const fullDescription = card.querySelector('.card-description').textContent;

            document.getElementById('modal-title').textContent = title;
            document.getElementById('modal-date').textContent = date;
            document.getElementById('modal-image').src = imgSrc;
            document.getElementById('modal-category').textContent = category;
            document.getElementById('modal-description').textContent = fullDescription;

            modalOverlay.style.display = 'flex';
        });
    });

    // Cerrar modal al hacer clic fuera de él
    modalOverlay.addEventListener('click', (event) => {
        if (event.target === modalOverlay) {
            modalOverlay.style.display = 'none';
        }
    });
});