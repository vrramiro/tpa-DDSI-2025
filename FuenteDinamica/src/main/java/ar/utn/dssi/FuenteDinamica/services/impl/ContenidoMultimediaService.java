package ar.utn.dssi.FuenteDinamica.services.impl;

import ar.utn.dssi.FuenteDinamica.mappers.MapperContenidoMultimedia;
import ar.utn.dssi.FuenteDinamica.models.entities.ContenidoMultimedia;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.models.entities.almacenadorMultimedia.AlmacenadorMultimedia;
import ar.utn.dssi.FuenteDinamica.models.repositories.IMultimediaRepository;
import ar.utn.dssi.FuenteDinamica.services.IContenidoMultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContenidoMultimediaService implements IContenidoMultimediaService {

  @Autowired
  private IMultimediaRepository multimediaRepository;

  @Autowired
  private AlmacenadorMultimedia almacenadorMultimedia;

  @Override
  public List<ContenidoMultimedia> crear(List<MultipartFile> archivos, Hecho hecho) {

    List<ContenidoMultimedia> multimedia = new ArrayList<>();

    if (archivos != null && !archivos.isEmpty()) {
      for (MultipartFile archivo : archivos) {
        String urlArchivo = almacenadorMultimedia.guardarArchivo(archivo);
        ContenidoMultimedia cm = MapperContenidoMultimedia.convertirAContenido(urlArchivo, hecho);
        this.multimediaRepository.save(cm);
        multimedia.add(cm);
      }
    }

    return multimedia;
  }

  //Elimino todos los archivos del hecho y los vuelvo a subir (es la unica forma simple que pense)
  //Por ahi se podria crear un controler para los multimedia que se encarge de la edicion pero no me copa la idea
  @Override
  public List<ContenidoMultimedia> editar(List<MultipartFile> files, Hecho hecho) {
    List<ContenidoMultimedia> contenidosActualesDelHecho = multimediaRepository.findByHecho(hecho);

    for (ContenidoMultimedia multimedia : contenidosActualesDelHecho) {
      almacenadorMultimedia.eliminarArchivo(multimedia.getUrl());
    }

    return this.crear(files, hecho);
  }
}
