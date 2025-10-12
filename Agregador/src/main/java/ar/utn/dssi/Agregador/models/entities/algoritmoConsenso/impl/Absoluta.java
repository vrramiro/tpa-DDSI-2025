package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Absoluta implements IAlgoritmoConsenso {
    /*
    public Boolean cumpleAlgoritmo(Hecho hecho, List<Fuente> fuentes) {
        return fuentes.stream().allMatch(fuente ->
                fuente.obtenerHechos().
                        stream().
                        anyMatch(otroHecho -> otroHecho.mismoHecho(hecho))
        );
    }*/

    @Override
    public List<Hecho> consensuar(List<Hecho> hechos, List<Fuente> fuentes) {
        return List.of();
    }
}


