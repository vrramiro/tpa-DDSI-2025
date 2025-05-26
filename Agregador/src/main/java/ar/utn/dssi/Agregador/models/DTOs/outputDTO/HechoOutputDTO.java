package ar.utn.dssi.Agregador.modelos.DTOs.outputDTO;


import ar.utn.dssi.Agregador.modelos.entidades.contenido.Categoria;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Etiqueta;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Ubicacion;

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