package ar.utb.ba.dsi.Normalizador.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Categoria {
  private Long id;
  private String nombre;

  @ElementCollection
  private List<String> categoriasExternas;
}
