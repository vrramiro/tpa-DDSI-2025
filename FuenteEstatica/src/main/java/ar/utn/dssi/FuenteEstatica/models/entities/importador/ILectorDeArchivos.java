package ar.utn.dssi.FuenteEstatica.models.entities.importador;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import java.io.File;
import java.util.List;

public interface ILectorDeArchivos {
  public List<Hecho> importarHechos(File archivo);
}