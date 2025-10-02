package ar.utn.dssi.FuenteProxy.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@Embeddable
public class Categoria {
  @Column(name = "categoria_id")
  private Long idCategoria;

  @Column(name = "categoria_nombre", nullable = false)
  private String nombre;
}
