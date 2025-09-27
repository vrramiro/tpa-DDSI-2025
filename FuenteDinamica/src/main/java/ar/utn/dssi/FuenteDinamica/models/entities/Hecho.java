package ar.utn.dssi.FuenteDinamica.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "hecho")
public class Hecho {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idHecho;

  @Embedded
  private Ubicacion ubicacion;

  @Column(name = "categoria", nullable = false)
  private String categoria;

  @OneToMany(mappedBy = "hecho", cascade = CascadeType.ALL)
  private List<ContenidoMultimedia> multimedia;

  @Column(name = "titula", nullable = false)
  private String titulo;

  @Column(name = "descripcion", nullable = false)
  private String descripcion;

  @Column(name = "fecha_acontecimiento", nullable = false)
  private LocalDateTime fechaAcontecimiento;

  @Column(name = "fecha_carga", nullable = false)
  private LocalDateTime fechaCarga;

  @Column(name = "fecha_edicion", nullable = false)
  private LocalDateTime fechaEdicion;

  private Boolean visible;
}