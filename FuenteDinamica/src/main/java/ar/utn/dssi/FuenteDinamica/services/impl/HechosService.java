package ar.utn.dssi.FuenteDinamica.services.impl;

import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.*;
import ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter.INormalizadorAdapter;
import ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter.impl.NormalizadorAdapter;
import ar.utn.dssi.FuenteDinamica.models.errores.DatosFaltantes;
import ar.utn.dssi.FuenteDinamica.models.errores.ErrorGeneralRepositorio;
import ar.utn.dssi.FuenteDinamica.models.errores.IdNoEncontrado;
import ar.utn.dssi.FuenteDinamica.models.errores.RepositorioVacio;
import ar.utn.dssi.FuenteDinamica.models.mappers.MapperContenidoMultimedia;
import ar.utn.dssi.FuenteDinamica.models.mappers.MapperDeHechos;
import ar.utn.dssi.FuenteDinamica.models.repositories.CategoriaRepository;
import ar.utn.dssi.FuenteDinamica.models.repositories.HechoRepository;
import ar.utn.dssi.FuenteDinamica.models.repositories.MultimediaRepository;
import ar.utn.dssi.FuenteDinamica.models.repositories.UbicacionRepository;
import ar.utn.dssi.FuenteDinamica.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class HechosService implements IHechosService {
  private LocalDateTime ultimoEnvioHechos;
  private List<HechoOutputDTO> hechosEditados;

  //REPOSITORIOS
  @Autowired
  private MultimediaRepository multimediaRepository;
  private HechoRepository hechoRepository;
  private CategoriaRepository categoriaRepository;
  private UbicacionRepository ubicacionRepository;
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

    hecho = this.hechoRepository.save(hecho);
    return MapperDeHechos.hechoOutputDTO(hecho);
  }

  ////////// READ //////////
  //Obtener todos los hechos
  @Override
  public List<HechoOutputDTO> obtenerHechos() {
    try {
      var hechos = this.hechoRepository.findAll();
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
    Hecho hecho = this.hechoRepository.findById(idHecho)
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
    Hecho hecho = this.hechoRepository.findById(idHecho)
            .orElseThrow(() -> new RuntimeException("Hecho no encontrado con id: " + idHecho));

    Long idCategoria = hechoInputDTO.getIdCategoria();
    Categoria categoria = this.categoriaRepository.findById(idCategoria)
            .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + idCategoria));

    return this.crear(hechoInputDTO);
  }


  ////////// DELETE //////////
  @Override
  public void eliminarHecho(Long idHecho){

    Hecho hecho = this.hechoRepository.findById(idHecho)
            .orElseThrow(() -> new RuntimeException("Hecho no encontrado con id: " + idHecho));

    this.hechoRepository.delete(hecho);
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
    Hecho hecho = hechoRepository.findById(idHecho)
            .orElseThrow(() -> new RuntimeException("Hecho no encontrado con id: " + idHecho));


    if(hecho == null) {
      throw new IdNoEncontrado("No existe el hecho con id: " + idHecho);
    }

    return ChronoUnit.DAYS.between(hecho.getFechaCarga(), LocalDateTime.now()) <= 7;
  }

}
