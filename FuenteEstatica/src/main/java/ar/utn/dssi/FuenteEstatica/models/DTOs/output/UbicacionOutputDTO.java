package ar.utn.dssi.FuenteEstatica.models.DTOs.output;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UbicacionOutputDTO {
    private Double latitud;
    private Double longitud;
    private String pais;
    private String ciudad;
    private String provincia;

}
