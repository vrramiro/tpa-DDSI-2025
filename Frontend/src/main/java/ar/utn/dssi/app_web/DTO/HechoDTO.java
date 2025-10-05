package ar.utn.dssi.app_web.DTO;

import lombok.Data;

import java.util.List;

@Data
public class HechoDTO {
    private String titulo;
    private String descripcion;
    private String categoria;
    private String ubicacion;
    private String longitud;
    private String latitud;
    private String fechaAcontecimiento;
    private String fechaCarga;
    private List<String> contenidoMultimedia;
}
