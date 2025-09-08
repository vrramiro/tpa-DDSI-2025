package ar.utn.dssi.FuenteProxy.models.entities.fuentes;

import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.Apis.DesastresNaturalesAPI;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.IServicioExternoAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.AdaptadoresConcretos.DesastresNaturalesAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.fuenteMetamapa.IFuenteMetaMapa;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.fuenteMetamapa.impl.FuenteMetaMapa;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ServicioExternoAdapterFactory {

    private final Map<TipoFuente, IServicioExternoAdapter> adapters;

    public ServicioExternoAdapterFactory(List<IServicioExternoAdapter> listaAdapters) {
        adapters = listaAdapters.stream()
                .collect(Collectors.toMap(IServicioExternoAdapter::getTipoFuente, Function.identity()));
    }

    public static IServicioExternoAdapter crearAdapter(TipoFuente tipoFuente) {
        IServicioExternoAdapter adapter = adapters.get(tipoFuente);
        if (adapter == null) {
            throw new IllegalArgumentException("Tipo de fuente desconocido: " + tipoFuente);
        }
        return adapter;
    }
}
