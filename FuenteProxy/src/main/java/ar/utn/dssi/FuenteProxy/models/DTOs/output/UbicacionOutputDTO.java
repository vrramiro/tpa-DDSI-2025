package ar.utn.dssi.FuenteProxy.models.DTOs.output;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UbicacionOutputDTO {
    private Double latitud;
    private Double longitud;

}
