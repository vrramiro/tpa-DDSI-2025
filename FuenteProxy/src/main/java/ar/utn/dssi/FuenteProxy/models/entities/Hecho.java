package ar.utn.dssi.FuenteProxy.models.entities;

import ar.utn.dssi.FuenteProxy.models.entities.fuentes.Fuente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "hecho")
public class Hecho {
  @Id
  private Long id;

  @Column(name = "titulo", nullable = false)
  private String titulo;

  @Column(name = "descripcion", nullable = false)
  private String descripcion;

  @ManyToOne
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

  @Embedded
  private Ubicacion ubicacion;

  @Column(name = "fecha_acontecimiento", nullable = false)
  private LocalDateTime fechaAcontecimiento;

  @Column(name = "fecha_carga", nullable = false)
  private LocalDateTime fechaCarga;

  @ManyToOne
  @JoinColumn(name = "fuente_id", nullable = false)
  private Fuente fuente;
}
