package ar.utn.dssi.Agregador.modelos.DTOs.inputDTO;

import ar.utn.dssi.Agregador.modelos.entidades.contenido.Categoria;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Ubicacion;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HechoInputDTO {
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Ubicacion ubicacion;  //TODO: consultar si podemos enviar datos de tipo
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
}