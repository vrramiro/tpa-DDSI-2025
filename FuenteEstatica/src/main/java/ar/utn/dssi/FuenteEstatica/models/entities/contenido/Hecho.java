package ar.utn.dssi.FuenteEstatica.models.entities.contenido;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

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

  @Embedded
  private Categoria categoria;

  @Embedded
  private Ubicacion ubicacion;

  @Column(name = "fecha_acontecimiento", nullable = false)
  private LocalDateTime fechaAcontecimiento;

  @CreatedDate
  @Column(name = "fecha_carga", nullable = false)
  private LocalDateTime fechaCarga;
}
