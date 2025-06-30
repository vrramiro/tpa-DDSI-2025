package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.AlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Absoluta extends AlgoritmoConsenso {
    @Override
    public Boolean cumpleAlgoritmo(Hecho hecho, List<Fuente> fuentes) {
        return fuentes.stream().allMatch(fuente ->
                fuente.obtenerHechos().
                        stream().
                        anyMatch(otroHecho -> otroHecho.mismoHecho(hecho))
        );
    }
}



