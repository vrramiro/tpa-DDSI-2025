package ar.utn.dssi.Agregador.models.entities.fuente.impl.fuenteDinamica;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoFuente;
import ar.utn.dssi.Agregador.models.mappers.MapperDeHechos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class FuenteDinamica implements ITipoFuente {
  private FuenteDinamicaConcreta fuenteDinamica;

  public FuenteDinamica() {
    this.fuenteDinamica = new FuenteDinamicaConcreta();
  }

  @Override
  public List<Hecho> hechosNuevos(Fuente fuente) {
    return this.fuenteDinamica.getHechos(fuente.getBaseUrl()).stream().map(MapperDeHechos::hecho).toList();
  }
}
