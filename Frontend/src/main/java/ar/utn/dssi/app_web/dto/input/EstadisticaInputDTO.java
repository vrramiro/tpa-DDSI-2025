package ar.utn.dssi.app_web.dto.input;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EstadisticaInputDTO {
    private String tipo;
    private String nombreColeccion;
    private String nombreCategoria;
    private Long valor;
    private String clave;
    private LocalDateTime fechaDeCalculo;
}
