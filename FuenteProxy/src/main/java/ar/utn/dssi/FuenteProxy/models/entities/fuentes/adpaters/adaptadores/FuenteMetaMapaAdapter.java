package ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.adaptadores;

import ar.utn.dssi.FuenteProxy.dto.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.IFuenteMetaMapaAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.concretos.MetaMapaConcreto;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.stream.Collectors;

public class FuenteMetaMapaAdapter implements IFuenteMetaMapaAdapter {
  private final MetaMapaConcreto metaMapaConcreto;

  public FuenteMetaMapaAdapter(MetaMapaConcreto metaMapaConcreto) {
    this.metaMapaConcreto = metaMapaConcreto;
  }

  @Override
  public Mono<List<Hecho>> obtenerHechos() {
    return metaMapaConcreto.obtenerHechos()
        .map(hechosMetaMapa -> hechosMetaMapa.getHechosInstanciaMetaMapa()
            .stream()
            .map(this::mapToHecho)
            .collect(Collectors.toList())
        );
  }

  private Hecho mapToHecho(HechoOutputDTO dto) {
    Hecho hecho = new Hecho();
    hecho.setTitulo(dto.getTitulo());
    hecho.setDescripcion(dto.getDescripcion());
    hecho.setCategoria(dto.getCategoria());
    hecho.setUbicacion(dto.getUbicacion());
    hecho.setFechaAcontecimiento(dto.getFechaAcontecimiento());
    hecho.setFechaCarga(dto.getFechaCarga());
    return hecho;
  }
}