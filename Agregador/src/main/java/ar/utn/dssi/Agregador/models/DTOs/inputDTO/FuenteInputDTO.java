package ar.utn.dssi.Agregador.models.DTOs.inputDTO;

import lombok.Data;

//Las fuentes de una coleccion se agregan por tipo, no por una fuente en especifico => por atras se relaciona la coleccion a todas las fuentes de ese tipo
//Logicamente dos fuentes del mismo tipo representan lo mismo para el usuario
@Data
public class FuenteInputDTO {
  private String tipoFuente;
}
