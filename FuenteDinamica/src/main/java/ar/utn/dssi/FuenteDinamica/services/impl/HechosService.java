package ar.utn.dssi.FuenteDinamica.services.impl;

import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.repositories.IHechosRepository;
import ar.utn.dssi.FuenteDinamica.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HechosService implements IHechosService {

  @Autowired
  private IHechosRepository hechosRepository;

  //TODO: logica de servicio
  public List<HechoOutputDTO> obtenerHechos() {
    return null; //TODO
  }
}
