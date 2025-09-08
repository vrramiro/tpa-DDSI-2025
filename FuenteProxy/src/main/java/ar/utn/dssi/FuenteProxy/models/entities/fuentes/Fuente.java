package ar.utn.dssi.FuenteProxy.models.entities.fuentes;

import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.IServicioExternoAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Fuente {
    private Long id;
    private String baseURL;
    private TipoFuente tipoFuente;
    private IServicioExternoAdapter servicioExternoAdapter;

    public Fuente(String baseURL, TipoFuente tipoFuente) {
        this.baseURL = baseURL;
        this.tipoFuente = tipoFuente;
        this.servicioExternoAdapter = ServicioExternoAdapterFactory.crearAdapter(tipoFuente);
    }

}
