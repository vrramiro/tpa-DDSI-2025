package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Absoluta implements IAlgoritmoDeConsenso{

  public boolean cumpleAlgoritmo(List<Hecho> hechos, Hecho hecho, List<Long> idsFuentes) {
    return idsFuentes
        .stream()
        .allMatch(fuente -> hechos
            .stream()
            .anyMatch(otroHecho -> otroHecho.getIdFuente().equals(fuente) && otroHecho.mismoHecho(hecho))
        );
  }
}

