package ar.utb.ba.dsi.estadisticas.models.adapters.agregador;

import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.ColeccionInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.HechoInputDTO;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class AgregadorAdapter implements IAgregadorAdapter {

    private final WebClient agregador;
    private String agregadorUrl;

    public AgregadorAdapter(WebClient agregador,String agregadorUrl) {
        this.agregador = agregador;
        this.agregadorUrl = agregadorUrl;
    }

    //TODO Falta el mapeo de lo que me llega a mis HechoInputDTO
    @Override
    public List<HechoInputDTO> obtenerHechos() {
        return agregador.get()
                .uri( agregadorUrl + "/hechos")
                .retrieve()
                .bodyToFlux(HechoInputDTO.class)
                .collectList()
                .block();
    }

    //TODO Falta el mapeo de lo que me llega a mis ColeccionInputDTO
    @Override
    public List<ColeccionInputDTO> obtenerColecciones() {
        return agregador.get()
                .uri( agregadorUrl + "/admin/colecciones")
                .retrieve()
                .bodyToFlux(ColeccionInputDTO.class)
                .collectList()
                .block();
    }
}
