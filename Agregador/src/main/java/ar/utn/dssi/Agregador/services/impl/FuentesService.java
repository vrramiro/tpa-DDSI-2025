package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.Origen;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.fuente.impl.TipoFuenteProxy;
import ar.utn.dssi.Agregador.services.IFuentesService;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
  public List<Hecho> obtenerNuevosHechos() {
    return this
        .fuentes
        .stream()
        .filter(fuente -> !(fuente.getTipoFuente() instanceof TipoFuenteProxy)) //SOLUCION A LOS ENUM
        .flatMap(fuente -> fuente.getTipoFuente().obtenerHechos().stream()
            .map(hechoInput -> {
              Hecho hecho = hechosService.crearHecho(hechoInput, fuente.getIdFuente());
              hechosService.guardarHecho(hecho);
              return hecho;
            })
        )
        .toList();
  }

  @Override
  public List<HechoInputDTO> obtenerHechosProxy() {
    return this
        .fuentes
        .stream()
        .filter(fuente -> fuente.getTipoFuente() instanceof TipoFuenteProxy) //SOLUCION A LOS ENUM
        .flatMap(fuente -> fuente.getTipoFuente().obtenerHechos().stream())
        .toList();
  }

  @Override
  public void eliminarHecho(Long IdEnFuente, Long IdFuenteOrigen){
  }

  public void agregarFuente(Fuente fuente) {
    this.fuentes.add(fuente);
  }

  public Fuente obtenerFuentePorId(Long idFuenteOrigen)
  {
    return this.fuentes.stream().filter(fuente -> fuente.getIdFuente().equals(idFuenteOrigen)).findFirst().orElse(null);
  }

  public List<Fuente> obtenerFuentes (){
    return this.fuentes;
  }
}
