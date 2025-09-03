package ar.utn.dssi.Agregador.models.entities.fuente;

import ar.utn.dssi.Agregador.models.entities.Origen;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.Mapper;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Fuente {
  private Long idFuente;
  private String url;
  private ITipoFuente tipoFuente;

  public Fuente(Long idFuente, String url, Origen tipoDeFuente) {
    this.idFuente = idFuente;
    this.url = url;
    this.tipoFuente = TipoFuenteFactory.crearTipoFuente(url, tipoDeFuente);
  }



  public List<Hecho> obtenerHechos() {
    return tipoFuente.obtenerHechos().stream().map(Mapper::hechoInputToHecho).toList();
  }

}