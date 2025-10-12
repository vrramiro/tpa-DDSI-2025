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
    switch (tipo) {
      case FECHA_DESDE:
        return new CriterioFechaDesde(LocalDate.parse(valorDelCriterio));
      case FECHA_HASTA:
        return new CriterioFechaHasta(LocalDate.parse(valorDelCriterio));
      case CATEGORIA:
        return new CriterioPorCategoria(valorDelCriterio);
      case PROVINCIA:
        return new CriterioPorProvincia(valorDelCriterio);

      default:
        return null;
    }
  }
}
