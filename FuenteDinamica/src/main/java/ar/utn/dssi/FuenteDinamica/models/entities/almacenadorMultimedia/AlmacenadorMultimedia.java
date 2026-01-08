package ar.utn.dssi.FuenteDinamica.models.entities.almacenadorMultimedia;

import ar.utn.dssi.FuenteDinamica.error.ArchivoMultimediaVacio;
import ar.utn.dssi.FuenteDinamica.error.DirectorioNoCreado;
import ar.utn.dssi.FuenteDinamica.error.FallaGuardadoArchivoMultimedia;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
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
        File directory = new File(directorioDeGuardado);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                // Imprimimos la ruta exacta para verla en los logs del PaaS
                System.err.println("ERROR CRÍTICO: No se pudo crear el directorio en: " + directory.getAbsolutePath());
                // No lances la excepción todavía para dejar que el servicio al menos levante
            }
        }
        System.out.println("Directorio de multimedia listo en: " + directory.getAbsolutePath());
    }

  public String guardarArchivo(MultipartFile archivo) {
    String nombreArchivoOriginal = archivo.getOriginalFilename();
    String extensionArchivo = "";
    int posicionPunto = nombreArchivoOriginal.lastIndexOf(".");

    if (posicionPunto > 0) {
      extensionArchivo = nombreArchivoOriginal.substring(posicionPunto);
    }

    String nombreArchivoGenerado = UUID.randomUUID() + extensionArchivo;

    try {
      if (archivo.isEmpty()) {
        throw new ArchivoMultimediaVacio("No se puede guardar el archivo " + nombreArchivoOriginal + " porque esta vacio.");
      }

      Path rutaDeArchivo = this.rutaAbsoluta.resolve(nombreArchivoGenerado);
      Files.copy(archivo.getInputStream(), rutaDeArchivo);

      return "/uploads/" + nombreArchivoGenerado;

    } catch (IOException e) {
      throw new FallaGuardadoArchivoMultimedia("No se pudo guardar el archivo: " + nombreArchivoOriginal);
    }
  }

  public void eliminarArchivo(String urlArchivo) {
    try {
      Files.deleteIfExists(Path.of(urlArchivo)); // Devuelve true si el archivo existía y fue borrado
    } catch (IOException e) {
      System.err.println("Error al eliminar el archivo " + urlArchivo + ": " + e.getMessage());
    }
  }

}
