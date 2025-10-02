package ar.utn.dssi.Agregador.models.DTOs.inputDTO;

import lombok.Data;

@Data
public class UbicacionInputDTO {
    private Double latitud;
    private Double longitud;
    private String pais;
    private String ciudad;
    private String provincia;
}
