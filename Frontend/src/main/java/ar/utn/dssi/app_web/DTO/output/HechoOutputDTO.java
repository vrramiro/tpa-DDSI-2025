package ar.utn.dssi.app_web.DTO.output;

import lombok.Data;

import java.util.List;

@Data
public class HechoOutputDTO {
    private String titulo;
    private String descripcion;
    private CategoriaOutputDTO categoria;
    private UbicacionOutputDTO ubicacion;
    private String fechaAcontecimiento;
    private String fechaCarga;
    private List<String> contenidoMultimedia;
}
