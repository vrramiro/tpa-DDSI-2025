package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.AlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MayoriaSimple extends AlgoritmoConsenso {

    @Override
    public Boolean cumpleAlgoritmo(Hecho hecho, List<Fuente> fuentes) {
        long coincidencias = fuentes.stream()
                .filter(fuente -> fuente.obtenerHechos().stream()
                        .anyMatch(hechoFuente -> hechoFuente.mismoHecho(hecho)))
                .count();

        return coincidencias >= fuentes.size()/2;
    }

}

