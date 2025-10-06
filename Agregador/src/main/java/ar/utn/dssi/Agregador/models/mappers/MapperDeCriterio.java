package ar.utn.dssi.Agregador.models.mappers;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.CriterioDePertenenciaInputDTO;
import ar.utn.dssi.Agregador.error.DatosDeColeccionFaltantes;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenenciaFactory;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.TipoCriterio;

public class MapperDeCriterio {
  public static CriterioDePertenencia criterioFromCriterioInputDTO(CriterioDePertenenciaInputDTO input) {
    if(input == null) throw new DatosDeColeccionFaltantes("El input no puede ser nulo");

    if(input.getTipo() == null || input.getTipo().isEmpty()) throw new DatosDeColeccionFaltantes("El tipo de criterio no puede ser nulo o vacío");

    if(input.getValor() == null || input.getValor().isEmpty()) throw new DatosDeColeccionFaltantes("El valor del criterio no puede ser nulo o vacío");

    String tipoInput = input.getTipo().trim().toUpperCase();

    try {
      TipoCriterio tipo = TipoCriterio.valueOf(tipoInput);
      return CriterioDePertenenciaFactory.crearCriterio(tipo, input.getValor());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Tipo de criterio no reconocido: " + input.getTipo());
    }
  }

  public static TipoCriterio tipoCriterioFromString(String tipo) {
    if(tipo == null || tipo.isEmpty()) throw new DatosDeColeccionFaltantes("El tipo de criterio no puede ser nulo o vacío");

    String tipoInput = tipo.trim().toUpperCase();

    try {
      return TipoCriterio.valueOf(tipoInput);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Tipo de criterio no reconocido: " + tipo);
    }
  }
}
