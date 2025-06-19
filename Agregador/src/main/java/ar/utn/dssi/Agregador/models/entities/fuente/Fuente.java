package ar.utn.dssi.Agregador.models.entities.fuente;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Origen;
import java.util.List;

public class Fuente {
  private Long idFuente;
  private String url;
  private ITipoFuente tipoFuente;

  public Fuente(Long idFuente, String url, Origen tipoFuente) {
    this.idFuente = idFuente;
    this.url = url;
    this.tipoFuente = TipoFuenteFactory.crearTipoFuente(url, tipoFuente);
  }

  public Boolean esDeTipo(Origen tipo) {
    return tipoFuente.tipo().equals(tipo);
  }

  public List<HechoInputDTO> obtenerHechos() {
    return tipoFuente.obtenerHechos();
  }
}
