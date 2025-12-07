package ar.utn.dssi.FuenteEstatica.models.entities.contenido;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "hecho")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hecho {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "titulo", nullable = false)
  private String titulo;

  @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
  private String descripcion;

  @Column(name = "tituloSanitizado", nullable = false)
  private String tituloSanitizado;

  @Column(name = "descripcionSanitizado", nullable = false, columnDefinition = "TEXT")
  private String descripcionSanitizado;

  @Embedded
  private Categoria categoria;

  @Embedded
  private Ubicacion ubicacion;

  @Column(name = "fecha_acontecimiento", nullable = false)
  private LocalDateTime fechaAcontecimiento;

  @CreatedDate
  @Column(name = "fecha_carga", nullable = false)
  private LocalDateTime fechaCarga;

  @Column(name = "autor")
  private String autor;
}
