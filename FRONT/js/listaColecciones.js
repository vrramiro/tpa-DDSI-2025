document.addEventListener("DOMContentLoaded", () => {
  const collectionModal = document.getElementById("collectionModal");
  const modalTitle = document.getElementById("modal-collection-title");
  const modalBody = document.getElementById("modal-table-body");
  const closeBtn = document.querySelector(".close-btn");

  const factModal = document.getElementById("factModal");
  const factClose = document.querySelector(".fact-close");

  const menuToggle = document.getElementById("menu-toggle");
  const mainNav = document.getElementById("main-nav");

  let currentCollection = [];
  let currentIndex = 0;

  // Toggle menú hamburguesa
  menuToggle.addEventListener("click", () => {
    mainNav.classList.toggle("show");
  });

  // Datos de ejemplo
  const collections = {
    "Protestas Sociales": [
      { titulo: "Manifestación por el clima", fecha: "2025-08-10", descripcion: "Gran marcha en defensa del medio ambiente.", categoria: "Protesta", imagen: "https://picsum.photos/400/200?random=1" },
      { titulo: "Huelga de transporte", fecha: "2025-07-05", descripcion: "Paro total de colectivos en el AMBA.", categoria: "Protesta", imagen: "https://picsum.photos/400/200?random=2" }
    ],
    "Eventos Ambientales": [
      { titulo: "Incendio forestal en la sierra", fecha: "2025-08-12", descripcion: "Afectó más de 200 hectáreas de bosque.", categoria: "Ambiental", imagen: "https://picsum.photos/400/200?random=3" },
      { titulo: "Inundación en el litoral", fecha: "2025-06-01", descripcion: "Varias ciudades afectadas por el desborde del río.", categoria: "Ambiental", imagen: "https://picsum.photos/400/200?random=4" }
    ],
    "Ayuda Humanitaria": [
      { titulo: "Entrega de víveres", fecha: "2025-05-20", descripcion: "ONG repartieron comida y agua en zonas afectadas.", categoria: "Ayuda", imagen: "https://picsum.photos/400/200?random=5" },
      { titulo: "Campaña de donación de sangre", fecha: "2025-04-15", descripcion: "Hospitales convocaron a la sociedad para donar.", categoria: "Salud", imagen: "https://picsum.photos/400/200?random=6" }
    ],
    "Educación y Estudiantes": [
      { titulo: "Marcha universitaria", fecha: "2025-03-10", descripcion: "Reclamo por mayor presupuesto en educación superior.", categoria: "Educación", imagen: "https://picsum.photos/400/200?random=7" },
      { titulo: "Reclamo por presupuesto educativo", fecha: "2025-02-25", descripcion: "Estudiantes pidieron más inversión en escuelas.", categoria: "Educación", imagen: "https://picsum.photos/400/200?random=8" }
    ]
  };

  // Abrir modal de colección
  document.querySelectorAll(".card-footer .btn-outline").forEach(btn => {
    btn.addEventListener("click", (e) => {
      const cardTitle = e.target.closest(".card").querySelector(".card-title").innerText;
      modalTitle.innerText = cardTitle;
      modalBody.innerHTML = "";

      collections[cardTitle].forEach((hecho, index) => {
        const row = document.createElement("tr");
        // MODIFICADO: Se añade la celda con el botón "Ver en Mapa"
        row.innerHTML = `
          <td class="fact-link" data-collection="${cardTitle}" data-index="${index}">${hecho.titulo}</td>
          <td>${hecho.fecha}</td>
          <td><button class="btn btn-map">Ver en Mapa</button></td>
        `;
        modalBody.appendChild(row);
      });

      collectionModal.style.display = "flex";
    });
  });

  // Click en un hecho -> abre factModal
  document.addEventListener("click", (e) => {
    if (e.target.classList.contains("fact-link")) {
      const col = e.target.dataset.collection;
      currentCollection = collections[col];
      currentIndex = parseInt(e.target.dataset.index);
      showFact(currentIndex);
      factModal.style.display = "flex";
    }
  });

  // Función para mostrar un hecho
  function showFact(index) {
    const hecho = currentCollection[index];
    document.getElementById("fact-title").innerText = hecho.titulo;
    document.getElementById("fact-date").innerText = hecho.fecha;
    document.getElementById("fact-description").innerText = hecho.descripcion;
    document.getElementById("fact-category").innerText = hecho.categoria;
    document.getElementById("fact-image").src = hecho.imagen;
  }

  // Navegación carrusel
  document.querySelector(".carousel-arrow.left").addEventListener("click", () => {
    currentIndex = (currentIndex - 1 + currentCollection.length) % currentCollection.length;
    showFact(currentIndex);
  });

  document.querySelector(".carousel-arrow.right").addEventListener("click", () => {
    currentIndex = (currentIndex + 1) % currentCollection.length;
    showFact(currentIndex);
  });

  // Cerrar modales
  closeBtn.addEventListener("click", () => { collectionModal.style.display = "none"; });
  factClose.addEventListener("click", () => { factModal.style.display = "none"; });
  window.addEventListener("click", (e) => {
    if (e.target === collectionModal) collectionModal.style.display = "none";
    if (e.target === factModal) factModal.style.display = "none";
  });
});