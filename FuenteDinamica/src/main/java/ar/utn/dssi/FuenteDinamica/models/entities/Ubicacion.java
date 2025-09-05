package ar.utn.dssi.FuenteDinamica.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "ubicacion")
public class Ubicacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double latitud;
  private Double longitud;
  private String pais;
  private String ciudad;
  private String provincia;
}