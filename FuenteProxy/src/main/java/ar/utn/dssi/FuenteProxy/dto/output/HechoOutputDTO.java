package ar.utn.dssi.FuenteProxy.dto.output;


import ar.utn.dssi.FuenteProxy.models.entities.Categoria;
import ar.utn.dssi.FuenteProxy.models.entities.Ubicacion;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class HechoOutputDTO {
  private Long idOrigen;
  private String titulo;
  private String descripcion;
  private String tituloSanitizado;
  private String descripcionSanitizada;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
} //TODO: CATEGORIA Y UBICACION NO DEBEN SER ENTIDADES DE DOMINIO