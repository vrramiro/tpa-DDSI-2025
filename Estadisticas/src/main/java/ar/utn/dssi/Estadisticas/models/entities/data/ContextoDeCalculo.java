package ar.utn.dssi.Estadisticas.models.entities.data;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ContextoDeCalculo {
  private List<Hecho> hechos;
  private List<Coleccion> colecciones;
  private Long solicitudDeEliminacion;
  private List<Categoria> categorias;
}
