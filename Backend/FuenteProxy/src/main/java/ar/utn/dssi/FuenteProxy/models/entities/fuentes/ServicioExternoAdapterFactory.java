package ar.utn.dssi.FuenteProxy.models.entities.fuentes;

import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.AdaptadoresConcretos.DesastresNaturalesAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.Apis.DesastresNaturalesApi;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.Apis.MetamapaApi;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.IServicioExternoAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.fuenteMetamapa.impl.FuenteMetaMapa;
import org.springframework.stereotype.Component;

@Component
public class ServicioExternoAdapterFactory {

    public static IServicioExternoAdapter crearAdapter(TipoFuente tipoFuente, String baseUrl) {
        IServicioExternoAdapter instancia = null;

        switch (tipoFuente) {
            case DESASTRES_NATURALES:
                instancia = new DesastresNaturalesAdapter(new DesastresNaturalesApi(baseUrl));
                break;
            case METAMAPA:
                instancia = new FuenteMetaMapa(new MetamapaApi(baseUrl));
                break;
            default:
                throw new IllegalArgumentException("Tipo de fuente no soportado: " + tipoFuente);
        }

        return instancia;
    }
}
