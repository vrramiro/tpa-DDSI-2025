package ar.utb.ba.dsi.Normalizador.models.DTOs.Output;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class HechoOutputDTO {
    private String titulo;
    private String descripcion;
    private String tituloSanitizado;
    private String descripcionSanitizada;
    private CategoriaOutputDTO categoria;
    private UbicacionOutputDTO ubicacion;
    private LocalDateTime fechaAcontecimiento;
}

