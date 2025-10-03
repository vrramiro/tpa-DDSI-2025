package ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia;

import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioFechaDesde;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioFechaHasta;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.CriterioPorCategoria;

import java.time.LocalDate;

public class CriterioDePertenenciaFactory {
    public static CriterioDePertenencia crearCriterio(TipoCriterio tipo, String valorDelCriterio) {
        switch(tipo) {
            case FECHA_DESDE:
                return new CriterioFechaDesde();
            case FECHA_HASTA:
                return new CriterioFechaHasta();
            case CATEGORIA:
                Categoria categoria = new Categoria();
                categoria.setNombre(valorDelCriterio);
                return new CriterioPorCategoria();
            /*case UBICACION:
                Ubicacion ubicacion = new Ubicacion();
                return new CriterioUbicacion();*/ //TODO: IMPLEMENTACION
            /*
            case FUENTE:
                return new CriterioPorFuente(Long.parseLong(valorDelCriterio));*/
            default:
                return null;
        }
    }
}
