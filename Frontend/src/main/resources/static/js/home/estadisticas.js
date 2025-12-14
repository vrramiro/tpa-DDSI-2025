document.addEventListener("DOMContentLoaded", () => {
    initListeners();
});

function initListeners() {
    // Listener para Colección -> Provincia
    const selectColeccion = document.getElementById("select-coleccion");
    if (selectColeccion) {
        selectColeccion.addEventListener("change", (e) => {
            const handle = e.target.value; // El valor del option es el handle
            actualizarEstadistica(
                `/estadisticas/api/coleccion/${handle}/provincia`,
                "res-col-valor",
                "res-col-clave"
            );
        });
    }

    // Listener para Categoría -> Provincia
    const selectCatProv = document.getElementById("select-cat-prov");
    if (selectCatProv) {
        selectCatProv.addEventListener("change", (e) => {
            const id = e.target.value;
            actualizarEstadistica(
                `/estadisticas/api/categoria/${id}/provincia`,
                "res-cat-prov-valor",
                "res-cat-prov-clave"
            );
        });
    }

    // Listener para Categoría -> Hora
    const selectCatHora = document.getElementById("select-cat-hora");
    if (selectCatHora) {
        selectCatHora.addEventListener("change", (e) => {
            const id = e.target.value;
            actualizarEstadistica(
                `/estadisticas/api/categoria/${id}/horas`,
                "res-cat-hora-valor",
                "res-cat-hora-clave"
            );
        });
    }
}

async function actualizarEstadistica(url, idValor, idClave) {
    const elValor = document.getElementById(idValor);
    const elClave = document.getElementById(idClave);

    // Estado de carga (Loading)
    elValor.style.opacity = '0.5';
    elClave.textContent = "Calculando...";

    try {
        const response = await fetch(url);

        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }

        const data = await response.json();
        // Se asume que data llega como: { clave: "Mendoza", valor: "150" }

        // Actualizar DOM
        elValor.textContent = data.valor || "0";
        elClave.textContent = data.clave || "Sin datos";

    } catch (error) {
        console.error("Error al obtener estadísticas:", error);
        elValor.textContent = "-";
        elClave.textContent = "Error al cargar";
    } finally {
        elValor.style.opacity = '1';
    }
}