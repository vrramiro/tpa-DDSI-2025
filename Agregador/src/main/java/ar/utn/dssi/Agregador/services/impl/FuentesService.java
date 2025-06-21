package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.external.HechosMetaMapa;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.FuenteServiceOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Origen;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.services.IFuentesService;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.List;

@Service
public class FuentesService implements IFuentesService {
  private List<Fuente> fuentes;

  @Autowired
  private IHechosService hechosService;

  public FuentesService() {
    this.fuentes = new ArrayList<>();
  }

  @Override
  public List<HechoInputDTO> obtenerNuevosHechos() {
    return this
        .fuentes
        .stream()
        .filter(fuente -> !fuente.esDeTipo(Origen.FUENTE_PROXY))
        .flatMap(fuente -> fuente.obtenerHechos().stream())
        .toList();
  }


  @Override
  public List<HechoInputDTO> obtenerHechosProxy() {
    return this.fuentes
        .stream()
        .filter(fuente -> fuente.esDeTipo(Origen.FUENTE_PROXY))
        .flatMap(fuente -> fuente.obtenerHechos().stream())
        .toList();
  }


  public void agregarFuente(Fuente fuente) {
    this.fuentes.add(fuente);
  }

  @Override
  public void eliminarHecho(Long IdEnFuente, Long IdFuenteOrigen){

  }
}
