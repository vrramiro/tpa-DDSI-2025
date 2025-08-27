// ============================================================
// MENU HAMBURGUESA (igual a landing.js)
// ============================================================
const menuToggle = document.getElementById("menu-toggle");
const mainNav = document.getElementById("main-nav");

menuToggle.addEventListener("click", () => {
  mainNav.classList.toggle("show");
});

// ============================================================
// FILTROS
// ============================================================
const btnFiltrar = document.getElementById("btn-filtrar");
btnFiltrar.addEventListener("click", () => {
  const desde = document.getElementById("fecha-desde").value;
  const hasta = document.getElementById("fecha-hasta").value;
  const origen = document.getElementById("origen").value;
  const categoria = document.getElementById("categoria").value;
  const ubicacion = document.getElementById("ubicacion").value;

  alert(
    `📌 Filtros seleccionados:\n\nFecha desde: ${desde}\nFecha hasta: ${hasta}\nOrigen: ${origen}\nCategoría: ${categoria}\nUbicación: ${ubicacion}`
  );
});

// ============================================================
// VER COLECCIONES
// ============================================================
const btnColecciones = document.getElementById("btn-colecciones");
btnColecciones.addEventListener("click", () => {
  alert("👉 Próximamente: vista de colecciones.");
  // Ejemplo: window.location.href = "colecciones.html";
});
