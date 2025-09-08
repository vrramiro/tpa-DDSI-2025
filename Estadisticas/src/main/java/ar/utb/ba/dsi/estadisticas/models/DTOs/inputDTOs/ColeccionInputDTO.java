package ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs;

import lombok.Data;

import java.util.List;

@Data
public class ColeccionInputDTO {
    private Long id;
    private String titulo;
    private List<HechoInputDTO> hechos;
}
