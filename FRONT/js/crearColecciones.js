// ============================================================
// MENU HAMBURGUESA (igual a landing.js)
// ============================================================
const menuToggle = document.getElementById("menu-toggle");
const mainNav = document.getElementById("main-nav");

menuToggle.addEventListener("click", () => {
  mainNav.classList.toggle("show");
});
