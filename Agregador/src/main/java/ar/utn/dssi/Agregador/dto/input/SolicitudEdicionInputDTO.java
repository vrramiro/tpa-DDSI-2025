package ar.utn.dssi.Agregador.dto.input;

import lombok.Data;

@Data
public class SolicitudEdicionInputDTO {
    private String titulo;
    private String descripcion;
    private Long idCategoria;
}