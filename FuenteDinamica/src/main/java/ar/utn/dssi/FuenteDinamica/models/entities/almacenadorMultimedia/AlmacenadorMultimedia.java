package ar.utn.dssi.FuenteDinamica.models.entities.almacenadorMultimedia;

import ar.utn.dssi.FuenteDinamica.error.ArchivoMultimediaVacio;
import ar.utn.dssi.FuenteDinamica.error.FallaGuardadoArchivoMultimedia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct; // Importante para Spring Boot 3
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class AlmacenadorMultimedia {

    @Value("${directorioDeGuardado}")
    private String directorioDeGuardado;

    private Path rutaAbsoluta;

    @PostConstruct
    public void init() {
        System.out.println("LOG_SISTEMA: Iniciando servicio...");
        try {
            this.rutaAbsoluta = Paths.get(directorioDeGuardado).toAbsolutePath().normalize();
            File directory = this.rutaAbsoluta.toFile();
            
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                System.out.println("LOG_SISTEMA: Directorio creado: " + created);
            }
            System.out.println("LOG_SISTEMA: Ruta activa en: " + rutaAbsoluta.toString());
        } catch (Exception e) {
            System.err.println("LOG_SISTEMA_ERROR: No se pudo preparar el disco: " + e.getMessage());
        }
    }
    
    public String guardarArchivo(MultipartFile archivo) {
        String nombreArchivoOriginal = archivo.getOriginalFilename();
        
        if (archivo.isEmpty()) {
            throw new ArchivoMultimediaVacio("El archivo " + nombreArchivoOriginal + " está vacío.");
        }

        String extensionArchivo = "";
        if (nombreArchivoOriginal != null && nombreArchivoOriginal.contains(".")) {
            extensionArchivo = nombreArchivoOriginal.substring(nombreArchivoOriginal.lastIndexOf("."));
        }

        String nombreArchivoGenerado = UUID.randomUUID().toString() + extensionArchivo;

        try {
            // 2. Ahora rutaAbsoluta ya no es null
            Path rutaDeArchivo = this.rutaAbsoluta.resolve(nombreArchivoGenerado);
            Files.copy(archivo.getInputStream(), rutaDeArchivo);

            return "/uploads/" + nombreArchivoGenerado;

        } catch (IOException e) {
            throw new FallaGuardadoArchivoMultimedia("No se pudo guardar el archivo: " + nombreArchivoOriginal);
        }
    }

    public void eliminarArchivo(String urlArchivo) {
        try {
            // Extraemos el nombre del archivo de la URL (/uploads/nombre.jpg -> nombre.jpg)
            String nombreArchivo = urlArchivo.replace("/uploads/", "");
            Path rutaAlArchivo = this.rutaAbsoluta.resolve(nombreArchivo);
            Files.deleteIfExists(rutaAlArchivo);
        } catch (IOException e) {
            System.err.println("Error al eliminar el archivo " + urlArchivo + ": " + e.getMessage());
        }
    }
}
