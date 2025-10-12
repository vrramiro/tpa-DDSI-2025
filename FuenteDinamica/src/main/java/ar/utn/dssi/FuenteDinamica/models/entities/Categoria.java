package ar.utn.dssi.FuenteDinamica.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@Entity
@Table(name = "Categoria")
public class Categoria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idCategoria;

  private String nombre;
}
