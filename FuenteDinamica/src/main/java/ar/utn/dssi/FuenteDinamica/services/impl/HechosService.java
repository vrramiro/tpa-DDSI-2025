package ar.utn.dssi.FuenteDinamica.services.impl;

import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.Errores.DatosFaltantes;
import ar.utn.dssi.FuenteDinamica.models.Errores.ErrorGeneralRepositorio;
import ar.utn.dssi.FuenteDinamica.models.Errores.IdNoEncontrado;
import ar.utn.dssi.FuenteDinamica.models.Errores.RepositorioVacio;
import ar.utn.dssi.FuenteDinamica.models.entities.Categoria;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.models.entities.Ubicacion;
import ar.utn.dssi.FuenteDinamica.models.repositories.ICategoriasRepository;
import ar.utn.dssi.FuenteDinamica.models.repositories.IHechosRepository;
import ar.utn.dssi.FuenteDinamica.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class HechosService implements IHechosService {
  @Autowired
  private IHechosRepository hechosRepository;

  @Autowired
  private ICategoriasRepository categoriasRepository;

  private LocalDateTime ultimoEnvioHechos;

  private List<HechoOutputDTO> hechosEditados;

  @Override
  public List<HechoOutputDTO> obtenerHechos() {
    try {
      var hechos = this.hechosRepository.findall();
      if (hechos.isEmpty()) {
        throw new RepositorioVacio("No hay hechos en la base de datos");
      }
      return hechos.stream()
              .map(this::hechoOutputDTO)
              .toList();
    } catch (Exception e) {
      throw new ErrorGeneralRepositorio("Error al obtener los hechos. ");
    }
  }

  @Override
  public List<HechoOutputDTO> obtenerHechosNuevos() {

    try {

      List<HechoOutputDTO> hechos = this.obtenerHechos();
      hechos.removeIf(hecho -> hecho.getFechaCarga().isAfter(ultimoEnvioHechos));

      if (hechos.isEmpty()) {
        throw new RepositorioVacio("No hay hechos en la base de datos");
      } return hechos;
    } catch (Exception e) {
      throw new ErrorGeneralRepositorio("Error al obtener los hechos.");
    }
  }


  @Override
  public HechoOutputDTO obtenerHechoPorId(Long idHecho) {
    Hecho hecho = this.hechosRepository.findById(idHecho);

    if (hecho == null) {
      throw new IdNoEncontrado("No existe el hecho con id: " + idHecho);
    }

    return this.hechoOutputDTO(hecho);
  }

  public HechoOutputDTO crear(HechoInputDTO hechoInputDTO) {
    // Validaciones manuales

    //TODO esta logica de validacion podria ir en una clase ValidadorHechosInput que implementa un metodo validar y recibe el hecho
    //encapsula la logica de validacion y lo vuelve mas extensible

    if (hechoInputDTO.getTitulo() == null || hechoInputDTO.getTitulo().isBlank()) {
      throw new DatosFaltantes("El título es obligatorio.");
    }

    if (hechoInputDTO.getDescripcion() == null || hechoInputDTO.getDescripcion().isBlank()) {
      throw new DatosFaltantes("La descripción es obligatoria.");
    }

    if (hechoInputDTO.getFechaAcontecimiento() == null) {
      throw new DatosFaltantes("La fecha de acontecimiento es obligatoria.");
    }

    if (hechoInputDTO.getFechaAcontecimiento().isAfter(LocalDateTime.now())) {
      throw new DatosFaltantes("La fecha de acontecimiento no puede ser futura.");
    }

    if (hechoInputDTO.getLatitud() == null || hechoInputDTO.getLongitud() == null) {
      throw new DatosFaltantes("Debe proporcionar tanto latitud como longitud para la ubicación.");
    }

    Categoria categoria = null;

    if(hechoInputDTO.getIdCategoria()!=null) {
        categoria = this.categoriasRepository.findById(hechoInputDTO.getIdCategoria());
        //TODO chequear si hay que hacer categoria service para hacer el manejo de errores aca
    }

    // Construcción del hecho

    var hecho = new Hecho();
    Ubicacion ubicacion = new Ubicacion();
      ubicacion.setLatitud(hechoInputDTO.getLatitud());
      ubicacion.setLongitud(hechoInputDTO.getLongitud());

    hecho.setTitulo(hechoInputDTO.getTitulo());
    hecho.setDescripcion(hechoInputDTO.getDescripcion());
    hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());
    hecho.setFechaCarga(LocalDateTime.now());
    hecho.setUbicacion(ubicacion);
    hecho.setCategoria(categoria);
    hecho.setContenidoMultimedia(hechoInputDTO.getContenidoMultimedia());
    hecho.setIdHecho(this.hechosRepository.obtenerUltimoId());
    hecho.setVisible(true);

    hecho = this.hechosRepository.save(hecho);

    return this.hechoOutputDTO(hecho);
  }

  @Override
  public void editarHecho(HechoInputDTO hecho, Long idHecho){
    if (hechoEditable(idHecho)) {
      HechoOutputDTO hechoActualizado = this.actualizarHecho(hecho, idHecho);

      hechosEditados.add(hechoActualizado);
    }
  }

  @Override
  public List<HechoOutputDTO> obtenerHechosEditados() {

    if (hechosEditados.isEmpty()) {
      throw new RepositorioVacio("No hay hechos en la base de datos");
    }

    List<HechoOutputDTO> hechosAEnviar; // TODO Revisar
    hechosAEnviar = this.hechosEditados;
    hechosEditados.clear();

    return hechosAEnviar;
  }

  @Override
  public Boolean hechoEditable(Long idHecho) {
    Hecho hecho = hechosRepository.findById(idHecho);

    if(hecho == null) {
      throw new IdNoEncontrado("No existe el hecho con id: " + idHecho);
    }

    return ChronoUnit.DAYS.between(hecho.getFechaCarga(), LocalDateTime.now()) <= 7;
  }

  @Override
  public void eliminarHecho(Long idHecho){

    if(hechosRepository.findById(idHecho) == null) {
      throw new IdNoEncontrado("No existe el hecho con id: " + idHecho);
    }

    this.hechosRepository.delete(idHecho);
  }

  private HechoOutputDTO actualizarHecho(HechoInputDTO hechoInputDTO, Long idHecho) {
    Hecho hecho = new Hecho();

    Ubicacion ubicacion = new Ubicacion();
      ubicacion.setLatitud(hechoInputDTO.getLatitud());
      ubicacion.setLongitud(hechoInputDTO.getLongitud());

    Categoria categoria = this.categoriasRepository.findById(hechoInputDTO.getIdCategoria());

    hecho.setTitulo(hechoInputDTO.getTitulo());
    hecho.setDescripcion(hechoInputDTO.getDescripcion());
    hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());
    hecho.setFechaCarga(LocalDateTime.now());
    hecho.setUbicacion(ubicacion);
    hecho.setCategoria(categoria);
    hecho.setContenidoMultimedia(hechoInputDTO.getContenidoMultimedia());
    hecho.setIdHecho(idHecho);

    try {
      this.hechosRepository.update(hecho);
    } catch (Exception e) {
      throw new ErrorGeneralRepositorio("Error al actualizar el hecho.");
    }

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
    dtoHecho.setContenidoMultimedia(hecho.getContenidoMultimedia());
    dtoHecho.setIdHechoOrigen(hecho.getIdHecho());
    return dtoHecho;
  }
}
