package ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia;

import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioFechaDesde;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioFechaHasta;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioPorCategoria;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioPorProvincia;

import java.time.LocalDate;

public class CriterioDePertenenciaFactory {
    //Si llega id null es porque es nuevo
    //Si llega id con valor es porque ya existia y se esta editando
    public static CriterioDePertenencia crearCriterio(Long id, TipoCriterio tipo, String valorDelCriterio) {
        switch(tipo) {
            case FECHA_DESDE:
                CriterioFechaDesde criterioFechaDesde = new CriterioFechaDesde();
                criterioFechaDesde.setFechaDesde(LocalDate.parse(valorDelCriterio));
                criterioFechaDesde.setId(id);
                return criterioFechaDesde;
            case FECHA_HASTA:
                CriterioFechaHasta criterioFechaHasta = new CriterioFechaHasta();
                criterioFechaHasta.setFechaHasta(LocalDate.parse(valorDelCriterio));
                criterioFechaHasta.setId(id);
                return criterioFechaHasta;
            case CATEGORIA:
                CriterioPorCategoria criterioCategoria = new CriterioPorCategoria();
                criterioCategoria.setCategoria(valorDelCriterio);
                criterioCategoria.setId(id);
                return criterioCategoria;
            case PROVINCIA:
                CriterioPorProvincia criterioProvincia = new CriterioPorProvincia();
                criterioProvincia.setProvincia(valorDelCriterio);
                criterioProvincia.setId(id);
                return criterioProvincia;
            default:
                return null;
        }
    }
}
