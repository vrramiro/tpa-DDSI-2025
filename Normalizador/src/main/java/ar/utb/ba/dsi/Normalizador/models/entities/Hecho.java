package ar.utb.ba.dsi.Normalizador.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hecho {
  private Long idHecho;
  private Ubicacion ubicacion;
  private Categoria categoria;
  private String titulo;
  private String descripcion;
  private String tituloSanitizado;
  private String descripcionSanitizada;
  private LocalDateTime fechaAcontecimiento;
}