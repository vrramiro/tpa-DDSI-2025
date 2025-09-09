package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.impl;


import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.AlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultiplesMenciones extends IAlgoritmoConsenso {

    @Value("${cantFuentesMultiplesMenciones}")
    static private Integer cantidadFuentes;

    @Override
    public Boolean cumpleAlgoritmo(Hecho hecho, List<Fuente> fuentes) {
        return this.fuentesLoTienen(hecho, fuentes) && this.ningunaLoTieneConDistintosAtributos(hecho, fuentes);
    }

    private Boolean fuentesLoTienen(Hecho hecho, List<Fuente> fuentes) {
        return fuentes.stream().
                filter(fuente -> fuente.obtenerHechos()
                        .stream()
                        .anyMatch(otroHecho -> otroHecho.mismoHecho(hecho)))
                .count() >= cantidadFuentes;
    }

    private Boolean ningunaLoTieneConDistintosAtributos(Hecho hecho, List<Fuente> fuentes) {
        return fuentes
                .stream()
                .flatMap(fuente -> fuente.obtenerHechos().stream())
                .noneMatch(otroHecho -> otroHecho.mismoMismoTitulo(hecho) && !otroHecho.mismosAtributos(hecho));
    }

    @Override
    public List<Hecho> consensuar(List<Hecho> hechos, List<Fuente> fuentes) {
        return List.of();
    }
}
