package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.impl;


import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.AlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultiplesMenciones extends AlgoritmoConsenso {

    @Override
    public Boolean cumpleAlgoritmo(Hecho hecho, List<Fuente> fuentes) {
        return this.dosFuentesLoTienen(hecho, fuentes) && this.ningunaLoTieneConDistintosAtributos(hecho, fuentes);
    }

    private Boolean dosFuentesLoTienen(Hecho hecho, List<Fuente> fuentes) {
        return fuentes.stream().
                filter(fuente -> fuente.obtenerHechos()
                        .stream()
                        .anyMatch(otroHecho -> otroHecho.mismoHecho(hecho)))
                .count() >= 2;
    }

    private Boolean ningunaLoTieneConDistintosAtributos(Hecho hecho, List<Fuente> fuentes) {
        return fuentes
                .stream()
                .flatMap(fuente -> fuente.obtenerHechos().stream())
                .noneMatch(otroHecho -> otroHecho.mismoMismoTitulo(hecho) && otroHecho.distintosAtributos(hecho));
    }
}
