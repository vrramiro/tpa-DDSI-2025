package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.impl;


import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.AlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import lombok.Setter;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultiplesMenciones extends AlgoritmoConsenso {

    @Setter
    static private Integer cantidadFuentes = 3; //lo inicializo en 3 pero si quiero lo puedo cambiar

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
}
