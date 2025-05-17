package ar.utn.dssi.FuenteEstatica.models.DTOs.output;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class HechoOutputDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Long idCategoria;
    private Ubicacion ubicacion;  //TODO: consultar si podemos enviar datos de tipo
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
}