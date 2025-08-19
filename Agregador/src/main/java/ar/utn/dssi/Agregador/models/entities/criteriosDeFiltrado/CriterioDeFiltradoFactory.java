package ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado;

import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.Ubicacion;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.impl.CriterioFechaDesde;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.impl.CriterioPorCategoria;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.impl.CriterioPorFuente;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.impl.CriterioUbicacion;

import java.time.LocalDate;

public class CriterioDeFiltradoFactory {
    public static ICriterioDeFiltrado crearCriterio(TipoCriterio tipo, String valorDelCriterio) {
        switch(tipo) {
            case FECHA_DESDE:
                return new CriterioFechaDesde(LocalDate.parse(valorDelCriterio));
            case FECHA_HASTA:
                return new CriterioFechaDesde(LocalDate.parse(valorDelCriterio));
            case CATEGORIA:
                Categoria categoria = new Categoria();
                categoria.setNombre(valorDelCriterio);
                return new CriterioPorCategoria(categoria);
            case UBICACION:
                Ubicacion ubicacion = new Ubicacion();
                return new CriterioUbicacion();
            case FUENTE:
                return new CriterioPorFuente((long) Integer.parseInt(valorDelCriterio));
            default:
                return null;
        }
    }
}
