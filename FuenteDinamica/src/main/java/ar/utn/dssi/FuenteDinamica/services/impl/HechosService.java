package ar.utn.dssi.FuenteDinamica.services.impl;

import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.*;
import ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter.INormalizadorAdapter;
import ar.utn.dssi.FuenteDinamica.models.errores.*;
import ar.utn.dssi.FuenteDinamica.models.mappers.MapperDeCategoria;
import ar.utn.dssi.FuenteDinamica.models.mappers.MapperDeHechos;
import ar.utn.dssi.FuenteDinamica.models.mappers.MapperDeUbicacion;
import ar.utn.dssi.FuenteDinamica.models.repositories.IHechoRepository;
import ar.utn.dssi.FuenteDinamica.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class HechosService implements IHechosService {

  private final INormalizadorAdapter normalizadorAdapter;

  public HechosService(@Qualifier("normalizadorAdapter") INormalizadorAdapter normalizadorAdapter) {
    this.normalizadorAdapter = normalizadorAdapter;
  }

  @Autowired
  private IHechoRepository hechoRepository;

  @Autowired
  private ContenidoMultimediaService contenidoMultimediaService;

  /*/////////////////////// OPERACIONES CRUD ///////////////////////*/
  /// /////// CREATE //////////
  @Override
  public void crear(HechoInputDTO hechoInputDTO) {
    validarHechoInput(hechoInputDTO);
    Hecho hechoANormalizar = MapperDeHechos.hechoFromInputDTO(hechoInputDTO);

    Hecho hechoNormalizado = normalizadorAdapter.obtenerHechoNormalizado(hechoANormalizar).block();

    hechoNormalizado.setFechaCarga(LocalDateTime.now());

    List<ContenidoMultimedia> contenidoMultimedia = this.contenidoMultimediaService
            .crear(hechoInputDTO.getContenidoMultimedia(), hechoNormalizado);

    hechoNormalizado.setMultimedia(contenidoMultimedia);

    hechoNormalizado.setVisible(true);

    this.hechoRepository.save(hechoNormalizado);
  }

  /// /////// READ //////////
  //Obtener todos los hechos
  @Override
  public List<HechoOutputDTO> obtenerHechos() {
    List<Hecho> hechos = this.hechoRepository.findAll();

    if (hechos.isEmpty()) {
      throw new RepositorioVacio("No hay hechos en la base de datos");
    }

    try {
      return hechos.stream()
              .map(MapperDeHechos::hechoOutputDTO)
              .toList();

    } catch (Exception e) {
      throw new ErrorGeneralRepositorio("Error al obtener los hechos. ");
    }
  }

  //Obtener todos los hechos nuevos
  @Override
  public List<HechoOutputDTO> obtenerHechosNuevos(LocalDateTime fechaDesde) {

    List<Hecho> hechos = this.hechoRepository.findHechosPorSubir(fechaDesde);

    if (hechos.isEmpty()) {
      throw new RepositorioVacio("No hay hechos en la base de datos");
    }

    try {
      return hechos.stream().map(MapperDeHechos::hechoOutputDTO).toList();
    } catch (Exception e) {
      throw new ErrorGeneralRepositorio("Error al obtener los hechos.");
    }
  }

  /// /////// UPDATE //////////
  @Override @Transactional
  public void editarHecho(HechoInputDTO hechoNuevo, Long idHecho) {
    Hecho hechoExistente = this.hechoRepository.findById(idHecho)
            .orElseThrow(() -> new RuntimeException("Hecho no encontrado con id: " + idHecho));

    validarEdicion(hechoExistente);

    actualizarCamposHecho(hechoExistente, hechoNuevo);

    try {
      this.hechoRepository.save(hechoExistente);
    }catch (Exception e) {
      throw new ErrorGeneralRepositorio("Error al actualizar el hecho con id: " + idHecho);
    }
  }

  /// /////// DELETE //////////
  @Override
  public void actualizarVisibilidad(Long idHecho, Boolean visibilidad) {
    try {
      Hecho hecho = this.hechoRepository.findById(idHecho)
              .orElseThrow(() -> new RuntimeException("Hecho no encontrado con id: " + idHecho));

      hecho.setVisible(visibilidad);

      this.hechoRepository.save(hecho);

    } catch (Exception e) {
      throw new ErrorGeneralRepositorio("Error al actualizar la visibilidad del hecho con id: " + idHecho);
    }
  }

  /*/////////////////////// FUNCIONES PRIVADAS ///////////////////////*/

  //Funcion para la validacion de hechos input
  private void validarHechoInput(HechoInputDTO hechoInputDTO) {
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
  }


  private void actualizarCamposHecho(Hecho hechoExistente, HechoInputDTO hechoNuevo) {
    hechoExistente.setTitulo(hechoNuevo.getTitulo());
    hechoExistente.setDescripcion(hechoNuevo.getDescripcion());
    hechoExistente.setCategoria(MapperDeCategoria.categoriaFromInputDTO(hechoNuevo.getCategoria()));
    hechoExistente.setFechaAcontecimiento(hechoNuevo.getFechaAcontecimiento());

    List<ContenidoMultimedia> contenidoMultimedia =
            this.contenidoMultimediaService.editar(hechoNuevo.getContenidoMultimedia(), hechoExistente);
    hechoExistente.setMultimedia(contenidoMultimedia);

    actualizarUbicacion(hechoExistente, hechoNuevo);

    hechoExistente.setFechaEdicion(LocalDateTime.now());
  }

  private void actualizarUbicacion(Hecho hechoExistente, HechoInputDTO hechoNuevo) {
    if (hechoExistente.getUbicacion().getLatitud() != hechoNuevo.getLatitud() ||
            hechoExistente.getUbicacion().getLongitud() != hechoNuevo.getLongitud()) {

      Ubicacion ubicacion = normalizadorAdapter
              .obtenerUbicacionNormalizada(
                      MapperDeUbicacion.ubicacionFromLatitudYLongitud(
                              hechoNuevo.getLatitud(),
                              hechoNuevo.getLongitud()
                      ))
              .block();

      hechoExistente.setUbicacion(ubicacion);
    }
  }

  private void validarEdicion(Hecho hecho) {
    if (ChronoUnit.DAYS.between(hecho.getFechaCarga(), LocalDateTime.now()) > 7) {
      throw new HechoNoEditable("El hecho con id: " + hecho.getIdHecho() + " no puede ser editado después de 7 días desde su carga.");
    }
  }
}