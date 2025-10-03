package ar.utn.dssi.FuenteDinamica.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
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

  @Column(name = "titulo", nullable = false)
  private String titulo;

  @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
  private String descripcion;

  @Column(name = "tituloSanitizado", nullable = false)
  private String tituloSanitizado;

  @Column(name = "descripcionSanitizado", nullable = false, columnDefinition = "TEXT")
  private String descripcionSanitizado;

  @Column(name = "fecha_acontecimiento", nullable = false)
  private LocalDateTime fechaAcontecimiento;

  @Embedded
  private Ubicacion ubicacion;

  @Embedded
  private Categoria categoria;

  @OneToMany(mappedBy = "hecho", cascade = CascadeType.ALL)
  private List<ContenidoMultimedia> multimedia;

  @Column(name = "fecha_carga", nullable = false)
  private LocalDateTime fechaCarga;

  @Column(name = "fecha_edicion")
  private LocalDateTime fechaEdicion;

  private Boolean visible;
}