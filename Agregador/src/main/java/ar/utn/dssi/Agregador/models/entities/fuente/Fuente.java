package ar.utn.dssi.Agregador.models.entities.fuente;

import ar.utn.dssi.Agregador.models.converters.TipoFuenteConverter;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "fuente")
@Getter
public class Fuente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, name = "fuente_id")
  private Long id;

  @Column(nullable = false, name = "nombre")
  private String nombre;

  @Convert(converter = TipoFuenteConverter.class)
  @Column(name = "tipo_fuente", nullable = false)
  private ITipoFuente tipoFuente;

  @Column(nullable = false, name = "base_url")
  private String baseUrl;

  @OneToMany(mappedBy = "fuente", fetch = FetchType.LAZY)
  private List<Hecho> hechos;

  @Setter @Column(name = "ultima_actualizacion")
  private LocalDateTime ultimaActualizacion;
}