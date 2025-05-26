package ar.utn.dssi.FuenteEstatica.models.DTOs.output;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Origen;
import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Ubicacion;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class HechoOutputDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Long idCategoria; //TODO Preguntar si es necesario hacer un id ya que sale de un archivo
    private Ubicacion ubicacion;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
    private Origen origen;
    private Boolean enviado;
}