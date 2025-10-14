package ar.utn.dssi.Agregador.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Embeddable
public class Categoria {
  @Column(nullable = false, name = "id_categoria")
  private Long id;

  @Column(nullable = false, name = "nombre_categoria")
  private String nombre;
}