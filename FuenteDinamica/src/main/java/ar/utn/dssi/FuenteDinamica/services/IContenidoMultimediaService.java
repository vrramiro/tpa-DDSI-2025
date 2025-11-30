package ar.utn.dssi.FuenteDinamica.services;

import ar.utn.dssi.FuenteDinamica.models.entities.ContenidoMultimedia;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface IContenidoMultimediaService {
  List<ContenidoMultimedia> crear(List<String> files, Hecho hecho);

  List<ContenidoMultimedia> editar(List<String> files, Hecho hecho);

}
