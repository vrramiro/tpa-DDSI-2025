package ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.AdaptadoresConcretos;

import ar.utn.dssi.FuenteProxy.models.DTOs.external.DesastresNaturales.HechoDesastresNaturales;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.Apis.DesastresNaturalesApi;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.IServicioExternoAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.Categoria;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.Ubicacion;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class DesastresNaturalesAdapter implements IServicioExternoAdapter {
    private final DesastresNaturalesApi api;

    public DesastresNaturalesAdapter(DesastresNaturalesApi api) {
        this.api = api;
    }

    @Override
    public Mono<List<Hecho>> obtenerHechos() {
        return api.autenticar("ddsi@gmail.com", "ddsi2025*")
                .flatMapMany(token -> obtenerTodasLasPaginas(token, 1))
                .map(this::mapearHecho)
                .collectList();
    }


    private Flux<HechoDesastresNaturales> obtenerTodasLasPaginas(String token, int pagina) {
        return api.obtenerHechosPorPagina(token, pagina, 100)
                .flatMapMany(hechos -> {
                    if (hechos.getData().isEmpty()) {
                        return Flux.empty();
                    }
                    Flux<HechoDesastresNaturales> actuales = Flux.fromIterable(hechos.getData());
                    if (hechos.getCurrent_page() >= hechos.getLast_page()) {
                        return actuales;
                    }
                    return actuales.concatWith(obtenerTodasLasPaginas(token, pagina + 1));
                });
    }

    private Hecho mapearHecho(HechoDesastresNaturales hechoObtenido) {
        Hecho hecho = new Hecho();

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setLatitud(hechoObtenido.getLatitud());
        ubicacion.setLongitud(hechoObtenido.getLongitud());

        Categoria categoria = new Categoria();
        categoria.setNombre(hechoObtenido.getCategoria());

        hecho.setTitulo(hechoObtenido.getTitulo());
        hecho.setDescripcion(hechoObtenido.getDescripcion());
        hecho.setCategoria(categoria);
        hecho.setUbicacion(ubicacion);
        hecho.setFechaAcontecimiento(hechoObtenido.getFecha_hecho());
        hecho.setFechaCarga(hechoObtenido.getCreated_at());

        return hecho;
    }
}

