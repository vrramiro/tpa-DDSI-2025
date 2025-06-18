package ar.utn.dssi.Agregador.models.entities.fuente;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Origen;
import java.util.List;

public class Fuente {
  private Long idFuente;
  private String url;
  private ITipoFuente tipoFuente;

  //TODO revisar como hacer para que las fuentes dinamicas y estaticas puedan traer hechos nuevos (no se si se gestiona exactamente aca)

  public Fuente(Long idFuente, String url, String tipoFuente) {
    this.idFuente = idFuente;
    this.url = url;
    this.tipoFuente = TipoFuenteFactory.crearTipoFuente(url, tipoFuente);
  }

  public List<HechoInputDTO> obtenerHechos() {
    return tipoFuente.obtenerHechos();
  }
}
