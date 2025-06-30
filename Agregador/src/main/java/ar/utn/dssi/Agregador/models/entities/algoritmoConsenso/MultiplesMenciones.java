package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultiplesMenciones implements IAlgoritmoDeConsenso{

    public boolean cumpleAlgoritmo(List<Hecho> hechos, Hecho hecho, List<Long> idsFuentes) {
        return idsFuentes.stream().
            filter(fuente -> hechos.stream().anyMatch(otroHecho -> otroHecho.mismoHecho(hecho)))
            .count() >= 2 && idsFuentes.stream().noneMatch(fuente -> hechos.stream().
                  anyMatch(otroHecho -> otroHecho.getIdFuente().equals(fuente) &&
                         otroHecho.mismoMismoTitulo(hecho) && otroHecho.
                            distintosAtributos(hecho)));
    }

}
