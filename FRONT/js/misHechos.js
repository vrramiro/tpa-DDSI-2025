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
          const description = card.querySelector('.card-description').textContent;

          document.getElementById('modal-title').textContent = title;
          document.getElementById('modal-date').textContent = date;
          document.getElementById('modal-image').src = imgSrc;
          document.getElementById('modal-category').textContent = category;
          document.getElementById('modal-description').textContent = description;

          modalOverlay.style.display = 'flex';
        });
      });

      modalOverlay.addEventListener('click', (event) => {
        if (event.target === modalOverlay) {
          modalOverlay.style.display = 'none';
        }
      });

      // --- Lógica del Menú Hamburguesa ---
      const hamburger = document.querySelector('.hamburger-menu');
      const nav = document.querySelector('nav');
      hamburger.addEventListener('click', () => {
        nav.classList.toggle('active');
      });
    });