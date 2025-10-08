package ar.utn.dssi.app_web.DTO.input;

import lombok.Data;

import java.util.List;

@Data
public class HechoInputDTO {
    private String titulo;
    private String descripcion;
    private CategoriaInputDTO categoria;
    private String longitud;
    private String latitud;
    private String fechaAcontecimiento;
    private String fechaCarga;
    private List<String> contenidoMultimedia;
}
