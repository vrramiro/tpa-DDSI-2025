package ar.utn.dssi.Agregador.models.mappers;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.CriterioDePertenenciaInputDTO;
import ar.utn.dssi.Agregador.error.DatosDeColeccionFaltantes;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenenciaFactory;
import java.time.LocalDate;

public class MapperDeCriterio {
  public static CriterioDePertenencia criterioFromCriterioInputDTO(CriterioDePertenenciaInputDTO input) {
    if(input == null) throw new DatosDeColeccionFaltantes("El input no puede ser nulo");

    if(input.getTipo() == null) throw new DatosDeColeccionFaltantes("El tipo de criterio no puede ser nulo o vacío");

    if(input.getValor() == null || input.getValor().isEmpty()) throw new DatosDeColeccionFaltantes("El valor del criterio no puede ser nulo o vacío");

    return CriterioDePertenenciaFactory.crearCriterio(input.getId(), input.getTipo(), input.getValor());
  }

  public static LocalDate parsearFecha(String fecha) {
    try {
      LocalDate fechaNueva = LocalDate.parse(fecha);

      if(fechaNueva.isAfter(LocalDate.now())) throw new IllegalArgumentException("La fecha no puede ser futura: " + fecha);

      return fechaNueva;
    } catch (Exception e) {
      throw new IllegalArgumentException("Formato de fecha inválido. Se esperaba AAAA-MM-DD, se recibió: " + fecha);
    }
  }
}
