package ar.utn.dssi.Estadisticas.models.entities.data;

import ar.utn.dssi.Estadisticas.models.entities.SolicitudDeEliminacion;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ContextoDeCalculo {
  private List<Hecho> hechos;
  private List<Coleccion> colecciones;
  private List<SolicitudDeEliminacion> solicitudDeEliminacion;
  private List<Categoria> categorias;
}
