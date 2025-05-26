package ar.utn.dssi.FuenteDinamica.services.impl;

import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.models.entities.Origen;
import ar.utn.dssi.FuenteDinamica.models.entities.Ubicacion;
import ar.utn.dssi.FuenteDinamica.models.repositories.ICategoriasRepository;
import ar.utn.dssi.FuenteDinamica.models.repositories.IHechosRepository;
import ar.utn.dssi.FuenteDinamica.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HechosService implements IHechosService {
  @Autowired
  private IHechosRepository hechosRepository;

  @Autowired
  private ICategoriasRepository categoriasRepository;

  public List<HechoOutputDTO> obtenerHechos() {
    try {
      var hechos = this.hechosRepository.findall();
      if (hechos.isEmpty()) {
        throw new RuntimeException("No hay hechos en la base de datos");
      }
      return hechos.stream()
              .map(this::hechoOutputDTO)
              .toList();
    } catch (Exception e) {
      throw new RuntimeException("Error al obtener los hechos: " + e.getMessage(), e);
    }
  }

  @Override
  public HechoOutputDTO obtenerHechoPorId(Long idHecho) {
      var hecho = this.hechosRepository.findById(idHecho);

      return this.hechoOutputDTO(hecho);
  }

  @Override
  public HechoOutputDTO crear(HechoInputDTO hechoInputDTO) {
    var hecho = new Hecho();
    var ubicacion = new Ubicacion(hechoInputDTO.getLatitud(), hechoInputDTO.getLongitud());
    var categoria = this.categoriasRepository.findById(hechoInputDTO.getIdCategoria());

    hecho.setTitulo(hechoInputDTO.getTitulo());
    hecho.setDescripcion(hechoInputDTO.getDescripcion());
    hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());
    hecho.setFechaCarga(LocalDateTime.now());
    hecho.setUbicacion(ubicacion);
    hecho.setCategoria(categoria);
    hecho.setOrigen(Origen.FUENTE_DINAMICA);

    hecho = this.hechosRepository.save(hecho);

    return this.hechoOutputDTO(hecho);
  }

  @Override
  public HechoOutputDTO hechoOutputDTO(Hecho hecho){
    var dtoHecho = new HechoOutputDTO();

    dtoHecho.setTitulo(hecho.getTitulo());
    dtoHecho.setDescripcion(hecho.getDescripcion());
    dtoHecho.setCategoria(hecho.getCategoria());
    dtoHecho.setUbicacion(hecho.getUbicacion());
    dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    dtoHecho.setFechaCarga(hecho.getFechaCarga());

    return dtoHecho;
  }
}
