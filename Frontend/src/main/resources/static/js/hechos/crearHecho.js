// Inicializar el mapa
let map = L.map('map').setView([-33.3017, -66.3378], 5);

L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors &copy; <a href="https://carto.com/attributions">CARTO</a>',
    subdomains: 'abcd',
    maxZoom: 20
}).addTo(map);

let marker = null;

function setMarker(lat, lng) {
    if (marker) {
        map.removeLayer(marker);
    }

    marker = L.marker([lat, lng]).addTo(map);
    map.setView([lat, lng], 13);

    document.getElementById('latitud').value = lat;
    document.getElementById('longitud').value = lng;
}

window.addEventListener('DOMContentLoaded', function() {
    const fechaInput = document.getElementById('fecha');
    if (fechaInput) {
        const hoy = new Date().toISOString().split('T')[0];
        fechaInput.setAttribute('max', hoy);
    }

    const latInput = document.getElementById('latitud');
    const lngInput = document.getElementById('longitud');

    if (latInput.value && lngInput.value) {
        const lat = parseFloat(latInput.value);
        const lng = parseFloat(lngInput.value);

        if (!isNaN(lat) && !isNaN(lng)) {
            setMarker(lat, lng);
        }
    }

});

map.on('click', function(e) {
    setMarker(e.latlng.lat, e.latlng.lng);
    console.log('Coordenadas seleccionadas:', e.latlng.lat, e.latlng.lng);
});

function cerrarModal() {
    const modal = document.getElementById('mensajeModal');
    if (modal) {
        const debeRedirigir = modal.getAttribute('data-redirect') === 'true';
        modal.style.display = 'none';

        if (debeRedirigir) {
            window.location.href = window.location.origin + '/hechos/explorador';
        }
    }
}

window.onload = function() {
    const modal = document.getElementById('mensajeModal');
    if (modal) {
        modal.style.display = 'flex';

        modal.addEventListener('click', function(e) {
            if (e.target === modal) {
                cerrarModal();
            }
        });
    }
}

document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        cerrarModal();
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const fileInput = document.getElementById('fileInput');
    const previewContainer = document.getElementById('preview-container');
    const hiddenInputsDiv = document.getElementById('hidden-inputs');
    const counterSpan = document.getElementById('upload-counter');
    const form = document.getElementById('formCrearHecho');
    const submitBtn = form ? form.querySelector('button[type="submit"]') : null;

    const urlInput = document.getElementById('config-url-upload');
    const URL_UPLOAD = urlInput ? urlInput.value : '';
    if (!URL_UPLOAD) {
        console.error("No se encontró la URL de subida multimedia.");
    }

    let archivosSubidos = [];

    if (form) {
        if (!form.querySelector('input[name="visible"]')) {
            const inputVisible = document.createElement('input');
            inputVisible.type = 'hidden';
            inputVisible.name = 'visible';
            inputVisible.value = 'true';
            form.appendChild(inputVisible);
            console.log("Campo 'visible' agregado al formulario.");
        }
    }

    if (fileInput) {
        fileInput.addEventListener('change', async function() {
            const files = Array.from(fileInput.files);
            if (files.length === 0) return;
            const errorMultimedia = document.getElementById('errorMultimedia');

            if (errorMultimedia) {
                errorMultimedia.style.display = 'none';
            }

            const archivosValidos = [];
            let hayArchivosInvalidos = false;

            files.forEach(file => {
                const esTipoValido = file.type === 'image/jpeg' ||
                    file.name.toLowerCase().endsWith('.jpg') ||
                    file.name.toLowerCase().endsWith('.jpeg');

                if (esTipoValido) {
                    archivosValidos.push(file);
                } else {
                    hayArchivosInvalidos = true;
                }
            });

            if (hayArchivosInvalidos && errorMultimedia) {
                errorMultimedia.style.display = 'inline-block';
            }

            if (archivosSubidos.length + archivosValidos.length > 5) {
                alert("Máximo 5 archivos permitidos.");
                fileInput.value = '';
                return;
            }

            if (archivosValidos.length === 0) {
                fileInput.value = '';
                return;
            }

            if(submitBtn) {
                submitBtn.disabled = true;
                submitBtn.innerText = "Subiendo...";
            }

            for (const file of archivosValidos) {
                const card = crearTarjetaVisual(file.name, true);
                previewContainer.appendChild(card);

                try {
                    const formData = new FormData();
                    formData.append('file', file);

                    const response = await fetch(URL_UPLOAD, { method: 'POST', body: formData });

                    if (response.ok) {
                        const ruta = await response.text();

                        marcarComoSubido(card, ruta);

                        agregarInputOculto(ruta);
                        archivosSubidos.push(ruta);
                    } else {
                        card.remove();
                        alert("Error al subir " + file.name);
                    }
                } catch (e) {
                    console.error(e);
                    card.remove();
                    alert("Error de conexión");
                }
            }

            actualizarContador();
            fileInput.value = '';

            if(submitBtn) {
                submitBtn.disabled = false;
                submitBtn.innerText = "Crear";
            }
        });
    }

    function crearTarjetaVisual(nombre, cargando) {
        const div = document.createElement('div');
        div.className = 'file-card' + (cargando ? ' loading' : '');
        div.innerHTML = `
                    <i class="fas fa-file-image"></i>
                    <span>${nombre.length > 10 ? nombre.substring(0,8)+'...' : nombre}</span>`;
        return div;
    }

    function marcarComoSubido(cardElement, ruta) {
        cardElement.classList.remove('loading');

        const btn = document.createElement('button');
        btn.className = 'btn-remove';
        btn.innerHTML = '&times;';
        btn.onclick = function() {
            borrarArchivo(cardElement, ruta);
        };

        cardElement.appendChild(btn);
    }

    function agregarInputOculto(ruta) {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'urlsContenidoMultimedia';
        input.value = ruta;
        input.id = 'input-' + ruta;
        hiddenInputsDiv.appendChild(input);
    }

    function borrarArchivo(cardElement, ruta) {
        cardElement.remove();

        const input = document.getElementById('input-' + ruta);
        if(input) input.remove();

        archivosSubidos = archivosSubidos.filter(u => u !== ruta);

        actualizarContador();
    }

    function actualizarContador() {
        counterSpan.innerText = `${archivosSubidos.length}/5`;
    }

    if (form) {
        form.addEventListener('submit', function(e) {
        });
    }
});