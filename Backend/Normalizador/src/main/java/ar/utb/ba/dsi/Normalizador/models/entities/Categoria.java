package ar.utb.ba.dsi.Normalizador.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table( name = "categoria")
@Getter
@Setter
@NoArgsConstructor
public class Categoria {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "categoria_id")
  private Long id;

  @Column(nullable = false)
  private String nombre;

  @ElementCollection
  @CollectionTable(
          name = "categorias_externas",
          joinColumns = @JoinColumn(name = "categoria_id")
  )
  private List<String> categoriasExternas;
}
