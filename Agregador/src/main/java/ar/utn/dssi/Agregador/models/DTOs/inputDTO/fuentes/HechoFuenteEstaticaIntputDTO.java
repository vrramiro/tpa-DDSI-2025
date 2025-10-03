package ar.utn.dssi.Agregador.models.DTOs.inputDTO.fuentes;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.CategoriaInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.UbicacionInputDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HechoFuenteEstaticaIntputDTO {
    private Long idExterno;

    private String titulo;
    private String descripcion;

    private String tituloSanitizado;
    private String descripcionSanitizada;

    private CategoriaInputDTO categoria;
    private UbicacionInputDTO ubicacion;

    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
}
