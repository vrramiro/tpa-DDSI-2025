package ar.utn.dssi.Agregador.modelos.DTOs.inputDTO;

import ar.utn.dssi.Agregador.modelos.entidades.contenido.Categoria;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Ubicacion;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HechoInputDTO {
    //private Long idHecho;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Ubicacion ubicacion;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
}

