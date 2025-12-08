package ar.utn.dssi.Agregador.dto.input.fuentes;

import ar.utn.dssi.Agregador.dto.input.CategoriaInputDTO;
import ar.utn.dssi.Agregador.dto.input.UbicacionInputDTO;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HechoFuenteEstaticaIntputDTO {
  private Long idOrigen;

  private String titulo;
  private String descripcion;

  private String tituloSanitizado;
  private String descripcionSanitizada;

  private CategoriaInputDTO categoria;
  private UbicacionInputDTO ubicacion;

  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
}
