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

  @ManyToOne
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

  @OneToMany(mappedBy = "hecho", cascade = CascadeType.ALL)
  private List<ContenidoMultimedia> multimedia;

  private String titulo;
  private String descripcion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;


  private Boolean visible;
}