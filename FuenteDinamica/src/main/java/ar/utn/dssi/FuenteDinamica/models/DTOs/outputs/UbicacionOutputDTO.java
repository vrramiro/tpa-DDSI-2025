package ar.utn.dssi.FuenteDinamica.models.DTOs.outputs;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UbicacionOutputDTO {
    private Double latitud;
    private Double longitud;

}
