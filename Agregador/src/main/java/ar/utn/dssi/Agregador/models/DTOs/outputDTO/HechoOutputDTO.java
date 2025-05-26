package ar.utn.dssi.Agregador.models.DTOs.outputDTO;


import ar.utn.dssi.Agregador.models.entities.content.Categoria;
import ar.utn.dssi.Agregador.models.entities.content.Etiqueta;
import ar.utn.dssi.Agregador.models.entities.content.Ubicacion;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoOutputDTO {
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Ubicacion ubicacion;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
    private List<Etiqueta> etiquetas;
}