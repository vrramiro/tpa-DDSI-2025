package ar.utb.ba.dsi.Normalizador.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
  private LocalDateTime fechaAcontecimiento;
}