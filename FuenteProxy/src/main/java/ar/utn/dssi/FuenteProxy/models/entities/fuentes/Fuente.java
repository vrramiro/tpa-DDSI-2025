package ar.utn.dssi.FuenteProxy.models.entities.fuentes;

import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.IServicioExternoAdapter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Mono;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "fuente")
public class Fuente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "url", nullable = false)
  private String baseUrl;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_fuente", nullable = false)
  private TipoFuente tipoFuente;

  @Transient
  private IServicioExternoAdapter servicioExternoAdapter;

  public Fuente(String baseUrl, TipoFuente tipoFuente) {
    this.baseUrl = baseUrl;
    this.tipoFuente = tipoFuente;
    this.servicioExternoAdapter = ServicioExternoAdapterFactory.crearAdapter(tipoFuente, baseUrl);
  }

  @PostLoad
  void postLoad() {
    this.servicioExternoAdapter = ServicioExternoAdapterFactory.crearAdapter(tipoFuente, baseUrl);
  }

  public Mono<List<Hecho>> importarHechos() {
    return this.servicioExternoAdapter.obtenerHechos()
        .map(hechos -> {
          hechos.forEach(hecho -> hecho.setFuente(this)
          );
          return hechos;
        });
  }
}
