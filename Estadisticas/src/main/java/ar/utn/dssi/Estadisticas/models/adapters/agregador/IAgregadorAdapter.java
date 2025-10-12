package ar.utn.dssi.Estadisticas.models.adapters.agregador;

import ar.utn.dssi.Estadisticas.models.entities.SolicitudDeEliminacion;
import ar.utn.dssi.Estadisticas.models.entities.data.Coleccion;
import ar.utn.dssi.Estadisticas.models.entities.data.Hecho;
import java.util.List;

public interface IAgregadorAdapter {
  public List<Hecho> obtenerHechos();

  public List<Coleccion> obtenerColecciones();

  public List<SolicitudDeEliminacion> obtenerSolicitudes();
}
