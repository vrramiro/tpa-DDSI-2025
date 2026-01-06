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
    try {
      // Forzamos una ruta relativa segura dentro del contenedor
      this.rutaAbsoluta = Paths.get(System.getProperty("user.dir"))
                               .resolve(directorioDeGuardado)
                               .normalize();
      
      if (!Files.exists(this.rutaAbsoluta)) {
          Files.createDirectories(this.rutaAbsoluta);
      }
      System.out.println(">> Almacenamiento activo en: " + this.rutaAbsoluta.toAbsolutePath());
    } catch (Exception e) {
      // LOGUEAR en lugar de lanzar THROW para que la app no se apague
      System.err.println("WARN: No se pudo inicializar el directorio de archivos: " + e.getMessage());
    }
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
