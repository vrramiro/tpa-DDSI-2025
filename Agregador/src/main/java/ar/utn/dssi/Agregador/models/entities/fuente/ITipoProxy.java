package ar.utn.dssi.Agregador.models.entities.fuente;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import java.util.List;

public interface ITipoProxy extends ITipoFuente {
  List<Hecho> hechosMetamapa(Fuente fuente);
}
