package ar.utn.dssi.FuenteProxy.models.entities.fuentes;

import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.adaptadores.DesastresNaturalesAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.concretos.DesastresNaturalesConcreto;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.concretos.MetaMapaConcreto;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.IServicioExternoAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.adaptadores.FuenteMetaMapaAdapter;
import org.springframework.stereotype.Component;

@Component
public class ServicioExternoAdapterFactory {

    public static IServicioExternoAdapter crearAdapter(TipoFuente tipoFuente, String baseUrl) {
        IServicioExternoAdapter instancia = switch (tipoFuente) {
          case DESASTRES_NATURALES -> new DesastresNaturalesAdapter(new DesastresNaturalesConcreto(baseUrl));
          case METAMAPA -> new FuenteMetaMapaAdapter(new MetaMapaConcreto(baseUrl));
          default -> throw new IllegalArgumentException("Tipo de fuente no soportado: " + tipoFuente);
        };

      return instancia;
    }
}
