package ar.utb.ba.dsi.estadisticas.models.adapters.agregador.impl;

import ar.utb.ba.dsi.estadisticas.models.entities.Coleccion;
import ar.utb.ba.dsi.estadisticas.models.entities.Hecho;
import ar.utb.ba.dsi.estadisticas.models.entities.SolicitudDeEliminacion;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class AgregadorAdapter implements IAgregadorAdapter {
    private final WebClient agregador;
    private String agregadorUrl;

    public AgregadorAdapter(WebClient agregador, String agregadorUrl) {
        this.agregador = agregador;
        this.agregadorUrl = agregadorUrl;
    }

    //TODO Falta el mapeo de lo que me llega a mis HechoInputDTO
    @Override
    public List<Hecho> obtenerHechos() {
        return agregador.get()
                .uri( agregadorUrl + "/hechos")
                .retrieve()
                .bodyToFlux(HechoInputDTO.class)
                .collectList()
                .block();
    }

    //TODO Falta el mapeo de lo que me llega a mis ColeccionInputDTO
    @Override
    public List<Coleccion> obtenerColecciones() {
        return agregador.get()
                .uri( agregadorUrl + "/admin/colecciones")
                .retrieve()
                .bodyToFlux(ColeccionInputDTO.class)
                .collectList()
                .block();
    }

    @Override
    public List<SolicitudDeEliminacion> obtenerSolicitudes() {
        return agregador.get()
                .uri(agregadorUrl + "/admin/solicitud/spam")
                .retrieve()
                .bodyToFlux(SolicitudDeEliminacionInputDTO.class)
                .collectList()
                .block();
    }
}
