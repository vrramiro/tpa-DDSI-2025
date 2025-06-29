package ar.utn.dssi.Agregador.models.entities.fuente;

import ar.utn.dssi.Agregador.models.entities.Origen;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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
    return tipoFuente.getTipo().equals(tipo);
  }
}
