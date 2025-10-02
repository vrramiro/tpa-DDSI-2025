package ar.utb.ba.dsi.Normalizador.models.entities;

import lombok.*;

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