package ar.utn.dssi.Agregador.models.entities.consensuador;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import java.util.List;

public interface IConsensuadorDeHechos {
  void consensuar(List<Hecho> hechos, List<Fuente> fuentes);

  void asignarClaveDeComparacion(Hecho hecho);
}
