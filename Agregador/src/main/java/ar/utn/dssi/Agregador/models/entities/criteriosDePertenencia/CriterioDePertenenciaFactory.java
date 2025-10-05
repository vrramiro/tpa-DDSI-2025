package ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia;

import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioFechaDesde;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioFechaHasta;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioPorCategoria;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioPorProvincia;

import java.time.LocalDate;

public class CriterioDePertenenciaFactory {
    public static CriterioDePertenencia crearCriterio(TipoCriterio tipo, String valorDelCriterio) {
        switch(tipo) {
            case FECHA_DESDE:
                CriterioFechaDesde criterioFechaDesde = new CriterioFechaDesde();
                criterioFechaDesde.setFechaDesde(LocalDate.parse(valorDelCriterio));
                return criterioFechaDesde;
            case FECHA_HASTA:
                CriterioFechaHasta criterioFechaHasta = new CriterioFechaHasta();
                criterioFechaHasta.setFechaHasta(LocalDate.parse(valorDelCriterio));
                return criterioFechaHasta;
            case CATEGORIA:
                CriterioPorCategoria criterioCategoria = new CriterioPorCategoria();
                criterioCategoria.setCategoria(valorDelCriterio);
                return criterioCategoria;
            case PROVINCIA:
                CriterioPorProvincia criterioProvincia = new CriterioPorProvincia();
                criterioProvincia.setProvincia(valorDelCriterio);
                return criterioProvincia;
            default:
                return null;
        }
    }
}
