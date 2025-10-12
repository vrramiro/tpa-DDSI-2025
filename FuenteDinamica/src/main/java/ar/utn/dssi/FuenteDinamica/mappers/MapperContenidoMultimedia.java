package ar.utn.dssi.FuenteDinamica.mappers;

import ar.utn.dssi.FuenteDinamica.models.entities.ContenidoMultimedia;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import java.util.List;
import java.util.stream.Collectors;

public class MapperContenidoMultimedia {

  public static ContenidoMultimedia convertirAContenido(String urlArchivo, Hecho hecho) {
    ContenidoMultimedia cm = new ContenidoMultimedia();
    cm.setUrl(urlArchivo);
    cm.setHecho(hecho);
    return cm;
  }

  public static List<String> obtenerUrlContenido(List<ContenidoMultimedia> contenidoMultimedia) {
    return contenidoMultimedia.stream().map(ContenidoMultimedia::getUrl).collect(Collectors.toList());
  }
}
