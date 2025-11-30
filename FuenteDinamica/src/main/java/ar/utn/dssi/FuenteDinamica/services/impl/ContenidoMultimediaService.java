package ar.utn.dssi.FuenteDinamica.services.impl;

import ar.utn.dssi.FuenteDinamica.mappers.MapperContenidoMultimedia;
import ar.utn.dssi.FuenteDinamica.models.entities.ContenidoMultimedia;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.models.entities.almacenadorMultimedia.AlmacenadorMultimedia;
import ar.utn.dssi.FuenteDinamica.models.repositories.IMultimediaRepository;
import ar.utn.dssi.FuenteDinamica.services.IContenidoMultimediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContenidoMultimediaService implements IContenidoMultimediaService {

  private final IMultimediaRepository multimediaRepository;
  private final AlmacenadorMultimedia almacenadorMultimedia;

  @Override
  public List<ContenidoMultimedia> crear(List<String> urls, Hecho hecho) {

    List<ContenidoMultimedia> multimediaList = new ArrayList<>();

    if (urls != null && !urls.isEmpty()) {
      for (String url : urls) {
        ContenidoMultimedia cm = new ContenidoMultimedia();
        cm.setUrl(url);     // Guardamos la ruta
        cm.setHecho(hecho); // Relacionamos con el padre

        this.multimediaRepository.save(cm); // Guardamos en BD
        multimediaList.add(cm);
      }
    }
    return multimediaList;
  }

  //Elimino todos los archivos del hecho y los vuelvo a subir (es la unica forma simple que pense)
  //Por ahi se podria crear un controler para los multimedia que se encarge de la edicion pero no me copa la idea
  @Override
  public List<ContenidoMultimedia> editar(List<String> nuevasUrls, Hecho hecho) {

    List<ContenidoMultimedia> multimediaViejos = multimediaRepository.findByHecho(hecho);
    for (ContenidoMultimedia viejo : multimediaViejos) {
      almacenadorMultimedia.eliminarArchivo(viejo.getUrl());
    }

    multimediaRepository.deleteAll(multimediaViejos);

    if(hecho.getMultimedia() != null) {
      hecho.getMultimedia().clear();
    }

    return this.crear(nuevasUrls, hecho);
  }
}
