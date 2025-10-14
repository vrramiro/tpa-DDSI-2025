package ar.utn.dssi.Agregador.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contenidos_multimedia")
public class ContenidoMultimedia {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "contenido_multimedia_id")
  private Long id;

  @Column(nullable = false, name = "url")
  private String url;

  public ContenidoMultimedia() {
  }

  public ContenidoMultimedia(String url) {
    this.url = url;
  }

}
