package ar.utn.dssi.FuenteDinamica.services.impl;

import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.*;
import ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter.INormalizadorAdapter;
import ar.utn.dssi.FuenteDinamica.models.errores.DatosFaltantes;
import ar.utn.dssi.FuenteDinamica.models.errores.ErrorGeneralRepositorio;
import ar.utn.dssi.FuenteDinamica.models.errores.IdNoEncontrado;
import ar.utn.dssi.FuenteDinamica.models.errores.RepositorioVacio;
import ar.utn.dssi.FuenteDinamica.models.mappers.MapperContenidoMultimedia;
import ar.utn.dssi.FuenteDinamica.models.mappers.MapperDeHechos;
import ar.utn.dssi.FuenteDinamica.models.repositories.ICategoriaRepository;
import ar.utn.dssi.FuenteDinamica.models.repositories.IHechoRepository;
import ar.utn.dssi.FuenteDinamica.models.repositories.IMultimediaRepository;
import ar.utn.dssi.FuenteDinamica.models.repositories.IUbicacionRepository;
import ar.utn.dssi.FuenteDinamica.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class HechosService implements IHechosService {
  private LocalDateTime ultimoEnvioHechos;
  private List<HechoOutputDTO> hechosEditados;

  //REPOSITORIOS
  @Autowired
  private IMultimediaRepository IMultimediaRepository;
  private IHechoRepository IHechoRepository;
  private ICategoriaRepository ICategoriaRepository;
  private IUbicacionRepository IUbicacionRepository;
  private INormalizadorAdapter normalizadorAdapter;


  /*/////////////////////// OPERACIONES CRUD ///////////////////////*/

  ////////// CREATE //////////
  @Override
  public HechoOutputDTO crear(HechoInputDTO hechoInputDTO) {
    validarHechoInput(hechoInputDTO);

    Hecho hecho = normalizadorAdapter
            .obtenerHechoNormalizado(MapperDeHechos.hechoFromInputToOutputNormalizador(hechoInputDTO)).block();

    hecho.setVisible(true);

    if (hechoInputDTO.getContenidoMultimedia() != null && !hechoInputDTO.getContenidoMultimedia().isEmpty()) {
      List<ContenidoMultimedia> multimedia = MapperContenidoMultimedia.convertirMultipartAContenido( hechoInputDTO.getContenidoMultimedia(), hecho);
      hecho.setMultimedia(multimedia);
    }

    hecho = this.IHechoRepository.save(hecho);
    return MapperDeHechos.hechoOutputDTO(hecho);
  }

  ////////// READ //////////
  //Obtener todos los hechos
  @Override
  public List<HechoOutputDTO> obtenerHechos() {
    try {
      var hechos = this.IHechoRepository.findAll();
      if (hechos.isEmpty()) {
        throw new RepositorioVacio("No hay hechos en la base de datos");
      }
      return hechos.stream()
              .map(MapperDeHechos::hechoOutputDTO)
              .toList();
    } catch (Exception e) {
      throw new ErrorGeneralRepositorio("Error al obtener los hechos. ");
    }
  }

  //Obtener todos los hechos nuevos
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

  //Obtener hechos por id
  @Override
  public HechoOutputDTO obtenerHechoPorId(Long idHecho) {
    Hecho hecho = this.IHechoRepository.findById(idHecho)
            .orElseThrow(() -> new RuntimeException("Hecho no encontrado con id: " + idHecho));
    return MapperDeHechos.hechoOutputDTO(hecho);
  }

  //Obtener los hechos que fueron editados
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

  ////////// UPDATE //////////
  @Override
  public void editarHecho(HechoInputDTO hecho, Long idHecho){
    if (hechoEditable(idHecho)) {
      HechoOutputDTO hechoActualizado = this.actualizarHecho(hecho, idHecho);

      hechosEditados.add(hechoActualizado);
    }
  }

  private HechoOutputDTO actualizarHecho(HechoInputDTO hechoInputDTO, Long idHecho) {
    Hecho hecho = this.IHechoRepository.findById(idHecho)
            .orElseThrow(() -> new RuntimeException("Hecho no encontrado con id: " + idHecho));

    Long idCategoria = hechoInputDTO.getIdCategoria();
    Categoria categoria = this.ICategoriaRepository.findById(idCategoria)
            .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + idCategoria));

    return this.crear(hechoInputDTO);
  }


  ////////// DELETE //////////
  @Override
  public void eliminarHecho(Long idHecho){

    Hecho hecho = this.IHechoRepository.findById(idHecho)
            .orElseThrow(() -> new RuntimeException("Hecho no encontrado con id: " + idHecho));

    this.IHechoRepository.delete(hecho);
  }

  /*/////////////////////// FUNCIONES PRIVADAS ///////////////////////*/

  //Funcion para la validacion de hechos input
  //TODO revisar posible builder/factory
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

  //Verificar si se puede editar el hecho
  private Boolean hechoEditable(Long idHecho) {
    Hecho hecho = IHechoRepository.findById(idHecho)
            .orElseThrow(() -> new RuntimeException("Hecho no encontrado con id: " + idHecho));


    if(hecho == null) {
      throw new IdNoEncontrado("No existe el hecho con id: " + idHecho);
    }

    return ChronoUnit.DAYS.between(hecho.getFechaCarga(), LocalDateTime.now()) <= 7;
  }

}
