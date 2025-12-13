package ar.utn.dssi.Estadisticas.models.entities.adapters.agregador;

import ar.utn.dssi.Estadisticas.models.entities.data.Coleccion;
import ar.utn.dssi.Estadisticas.models.entities.data.Hecho;
import java.util.List;

public interface IAgregadorAdapter {
  List<Hecho> obtenerHechos();

  List<Coleccion> obtenerColecciones();

  Long obtenerSolicitudesSpam();
}
