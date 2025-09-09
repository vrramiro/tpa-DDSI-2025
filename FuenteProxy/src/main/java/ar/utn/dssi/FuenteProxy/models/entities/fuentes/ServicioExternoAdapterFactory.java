package ar.utn.dssi.FuenteProxy.models.entities.fuentes;

import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.AdaptadoresConcretos.DesastresNaturalesAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.Apis.DesastresNaturalesAPI;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.Apis.MetamapaApi;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.IServicioExternoAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.fuenteMetamapa.impl.FuenteMetaMapa;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ServicioExternoAdapterFactory {

    public static IServicioExternoAdapter crearAdapter(TipoFuente tipoFuente) {
        IServicioExternoAdapter instancia = null;

        switch (tipoFuente) {
            case DESASTRES_NATURALES:
                instancia = new DesastresNaturalesAdapter(new DesastresNaturalesAPI());
                break;
            case METAMAPA:
                instancia = new FuenteMetaMapa(new MetamapaApi());
                break;
            default:
                throw new IllegalArgumentException("Tipo de fuente no soportado: " + tipoFuente);
        }

        return instancia;
    }
}
