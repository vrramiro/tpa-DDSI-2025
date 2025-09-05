package ar.utn.dssi.Agregador.models.entities.fuente;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface ITipoFuente {
  List<Hecho> hechosNuevos(Fuente fuente);
}
