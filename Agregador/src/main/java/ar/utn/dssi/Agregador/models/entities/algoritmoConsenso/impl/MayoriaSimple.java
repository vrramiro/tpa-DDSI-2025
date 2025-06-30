package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.impl;

import ar.utn.dssi.Agregador.models.entities.Consenso;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoDeConsenso;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MayoriaSimple implements IAlgoritmoDeConsenso {
    private Consenso consenso;

    public MayoriaSimple() {
        this.consenso = Consenso.MAYORIA_SIMPLE;
    }

    public Boolean cumpleAlgoritmo(List<Hecho> hechos, Hecho hecho, List<Long> idsFuentes) {
        Integer cantidadDeFuentesEnColeccion = idsFuentes.size();
        Long contienenHecho = idsFuentes.stream().filter
            (fuente -> hechos.stream().anyMatch(otroHecho ->
                    otroHecho.getIdFuente().equals(fuente) && otroHecho.mismoHecho(hecho))
            ).count();
        return contienenHecho >= cantidadDeFuentesEnColeccion/2;
    }
}

