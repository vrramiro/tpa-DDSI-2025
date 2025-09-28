package ar.utn.dssi.FuenteProxy.models.entities.fuentes;

import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.IServicioExternoAdapter;
import jakarta.persistence.*;
import lombok.*;
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

    public Mono<List<Hecho>> importarHechos(){
        return this.servicioExternoAdapter.obtenerHechos()
            .map(hechos -> {;
                hechos.forEach(hecho -> hecho.setFuente(this));
                return hechos;
            });
    }
}
