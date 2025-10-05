package ar.utn.dssi.Agregador.models.mappers;

import ar.utn.dssi.Agregador.error.DatosDeColeccionFaltantes;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.TipoConsenso;

public class MapperDeConsenso {
  public static TipoConsenso consensoFromConsensoInputDTO(String consenso) {
    if(consenso == null || consenso.isEmpty()) throw new DatosDeColeccionFaltantes("El algoritmo de consenso es obligatorio.");

    String consensoInput = consenso.trim().toUpperCase();

    try {
      return TipoConsenso.valueOf(consenso.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new DatosDeColeccionFaltantes("El algoritmo de consenso ingresado no es reconocido:" + consensoInput);
    }

  }
}
