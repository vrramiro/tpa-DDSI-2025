document.addEventListener("DOMContentLoaded", () => {
    initListeners();
});

function initListeners() {
    const spamCounter = document.getElementById("cant-solicitudes-spam");

    if (spamCounter) {

        let texto = spamCounter.textContent.trim();


        if (!texto || texto === "null" || texto === "") {
            spamCounter.textContent = "0";
        }
    }

    const selectColeccion = document.getElementById("select-coleccion");
    if (selectColeccion) {
        selectColeccion.addEventListener("change", (e) => {
            actualizarEstadistica(
                `/estadisticas/api/coleccion/${e.target.value}/provincia`,
                "res-col-valor",
                "res-col-clave"
            );
        });
    }

    const selectCatProv = document.getElementById("select-cat-prov");
    if (selectCatProv) {
        selectCatProv.addEventListener("change", (e) => {
            actualizarEstadistica(
                `/estadisticas/api/categoria/${e.target.value}/provincia`,
                "res-cat-prov-valor",
                "res-cat-prov-clave"
            );
        });
    }

    const selectCatHora = document.getElementById("select-cat-hora");
    if (selectCatHora) {
        selectCatHora.addEventListener("change", (e) => {
            actualizarEstadistica(
                `/estadisticas/api/categoria/${e.target.value}/horas`,
                "res-cat-hora-valor",
                "res-cat-hora-clave"
            );
        });
    }
}

async function actualizarEstadistica(url, idValor, idClave) {
    const elValor = document.getElementById(idValor);
    const elClave = document.getElementById(idClave);

    // Feedback visual simple
    elValor.style.opacity = '0.5';
    elClave.textContent = "Calculando...";

    // Limpiamos clases de "Sin datos" por si estaban puestas de antes
    elValor.classList.remove('estado-sin-datos');
    elClave.classList.remove('descripcion-sin-datos');

    try {
        const response = await fetch(url);
        if (!response.ok) throw new Error(`Error HTTP: ${response.status}`);

        const text = await response.text();

        const data = text ? JSON.parse(text) : null;

        if (data && data.valor && data.valor !== "null") {
            elValor.textContent = data.valor;
            elClave.textContent = data.clave || "";
        } else {
            aplicarFormatoSinDatos(elValor, elClave);
        }

    } catch (error) {
        console.error("Error stats:", error);
        aplicarFormatoSinDatos(elValor, elClave, "Error de carga");
    } finally {
        elValor.style.opacity = '1';
    }
}

function aplicarFormatoSinDatos(elValor, elClave, mensaje = "Sin datos") {
    elValor.textContent = "-";
    elClave.textContent = mensaje;

    // Aplicamos las clases CSS que usan var(--primary-color)
    elValor.classList.add('estado-sin-datos');
    elClave.classList.add('descripcion-sin-datos');
}