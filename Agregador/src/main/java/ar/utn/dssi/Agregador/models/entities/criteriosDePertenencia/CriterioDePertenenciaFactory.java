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
                LocalDate fechaDesde = LocalDate.parse(valorDelCriterio);
                return new CriterioFechaDesde(fechaDesde);
            case FECHA_HASTA:
                LocalDate fechaHasta = LocalDate.parse(valorDelCriterio);
                return new CriterioFechaHasta(fechaHasta);
            case CATEGORIA:
                Categoria categoria = new Categoria();
                categoria.setNombre(valorDelCriterio);
                CriterioPorCategoria criterioCategoria = new CriterioPorCategoria();
                criterioCategoria.agregarCategorias(categoria);
                return criterioCategoria;
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
