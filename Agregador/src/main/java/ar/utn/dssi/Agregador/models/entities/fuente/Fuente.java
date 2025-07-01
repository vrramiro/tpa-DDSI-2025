package ar.utn.dssi.Agregador.models.entities.fuente;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

  //public Boolean esDeTipo(Origen tipo) {
    //return tipoFuente.getTipo().equals(tipo);
  //}

  public List<Hecho> obtenerHechos() {
    return tipoFuente.obtenerHechos().stream().map(Mapper::hechoInputToHecho).toList();
  }

}