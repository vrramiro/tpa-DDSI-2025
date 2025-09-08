package ar.utn.dssi.Estadisticas.models.adapters.agregador.impl;

import ar.utn.dssi.Estadisticas.models.DTOs.inputs.ColeccionInputDTO;
import ar.utn.dssi.Estadisticas.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.Estadisticas.models.DTOs.inputs.SolicitudDeEliminacionInputDTO;
import ar.utn.dssi.Estadisticas.models.adapters.agregador.IAgregadorAdapter;
import ar.utn.dssi.Estadisticas.models.entities.data.Coleccion;
import ar.utn.dssi.Estadisticas.models.entities.data.Hecho;
import ar.utn.dssi.Estadisticas.models.entities.SolicitudDeEliminacion;
import ar.utn.dssi.Estadisticas.models.mappers.MapperDeColecciones;
import ar.utn.dssi.Estadisticas.models.mappers.MapperDeHechos;
import ar.utn.dssi.Estadisticas.models.mappers.MapperDeSolicitudEliminacion;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class AgregadorAdapter implements IAgregadorAdapter {
    private final WebClient agregador;
    private String agregadorUrl;

    public AgregadorAdapter(WebClient agregador, String agregadorUrl) {
        this.agregador = agregador;
        this.agregadorUrl = agregadorUrl;
    }

    @Override
    public List<Hecho> obtenerHechos() {
        return agregador.get()
                .uri( agregadorUrl + "/hechos")
                .retrieve()
                .bodyToFlux(HechoInputDTO.class)
                .map(MapperDeHechos :: hechoFromInput)
                .collectList()
                .block();
    }

    @Override
    public List<Coleccion> obtenerColecciones() {
        return agregador.get()
                .uri( agregadorUrl + "/admin/colecciones")
                .retrieve()
                .bodyToFlux(ColeccionInputDTO.class)
                .map(MapperDeColecciones :: coleccionFromInputDTO)
                .collectList()
                .block();
    }

    @Override
    public List<SolicitudDeEliminacion> obtenerSolicitudes() {
        return agregador.get()
                .uri(agregadorUrl + "/admin/solicitud/spam")
                .retrieve()
                .bodyToFlux(SolicitudDeEliminacionInputDTO.class)
                .map(MapperDeSolicitudEliminacion :: solicitudFromInput)
                .collectList()
                .block();
    }
}
