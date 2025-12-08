package ar.utn.dssi.FuenteDinamica.services.impl;

import ar.utn.dssi.FuenteDinamica.dto.input.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.dto.output.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.error.DatosFaltantes;
import ar.utn.dssi.FuenteDinamica.error.ErrorGeneralRepositorio;
import ar.utn.dssi.FuenteDinamica.error.HechoNoEditable;
import ar.utn.dssi.FuenteDinamica.error.RepositorioVacio;
import ar.utn.dssi.FuenteDinamica.mappers.MapperDeHechos;
import ar.utn.dssi.FuenteDinamica.models.entities.Categoria;
import ar.utn.dssi.FuenteDinamica.models.entities.ContenidoMultimedia;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.models.entities.Ubicacion;
import ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter.INormalizadorAdapter;
import ar.utn.dssi.FuenteDinamica.models.repositories.IHechoRepository;
import ar.utn.dssi.FuenteDinamica.services.IHechosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class HechosService implements IHechosService {

  private final INormalizadorAdapter normalizadorAdapter;
  private final IHechoRepository hechoRepository;
  private final ContenidoMultimediaService contenidoMultimediaService;

  @Override
  public void crear(HechoInputDTO hechoInputDTO) {
    detectarSpamHechos(hechoInputDTO);
    validarHechoInput(hechoInputDTO);

    Hecho hechoANormalizar = obtenerHechoANormalizar(hechoInputDTO);
    hechoANormalizar.setUbicacion(ubicacionNormalizada(hechoInputDTO));
    Hecho hechoNormalizado = normalizadorAdapter.obtenerHechoNormalizado(hechoANormalizar).block();
    hechoNormalizado.setFechaCarga(LocalDateTime.now());

    List<ContenidoMultimedia> contenidoMultimedia = this.contenidoMultimediaService
            .crear(hechoInputDTO.getUrlsContenidoMultimedia(), hechoNormalizado);

    hechoNormalizado.setMultimedia(contenidoMultimedia);
    hechoNormalizado.setVisible(true);

    if (Boolean.TRUE.equals(hechoInputDTO.getAnonimo())) {
      hechoNormalizado.setAutor(null);
    } else {
      String autor = obtenerAutorActual();
      hechoNormalizado.setAutor(autor);
    }

    this.hechoRepository.save(hechoNormalizado);
  }

  @Override
  public List<HechoOutputDTO> obtenerHechos(LocalDateTime fechaDesde) {

    List<Hecho> hechos = this.hechoRepository.findHechosByFechaLimite(fechaDesde);

    if (hechos.isEmpty()) {
      throw new RepositorioVacio("No hay hechos en la base de datos");
    }

    try {
      return hechos.stream().map(MapperDeHechos::hechoOutputDTO).toList();
    } catch (Exception e) {
      throw new ErrorGeneralRepositorio("Error al obtener los hechos.");
    }
  }

  @Override
  @Transactional
  public void editarHecho(HechoInputDTO hechoNuevo, Long idHecho) {
    Hecho hechoExistente = this.hechoRepository.findById(idHecho)
        .orElseThrow(() -> new RuntimeException("Hecho no encontrado con id: " + idHecho));

    validarEdicion(hechoExistente);

    actualizarCamposHecho(hechoExistente, hechoNuevo);

    try {
      this.hechoRepository.save(hechoExistente);
    } catch (Exception e) {
      throw new ErrorGeneralRepositorio("Error al actualizar el hecho con id: " + idHecho);
    }
  }

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

  private void validarHechoInput(HechoInputDTO hechoInputDTO) {
    if (hechoInputDTO.getTitulo() == null || hechoInputDTO.getTitulo().isBlank()) {
      throw new DatosFaltantes("El título es obligatorio.");
    }
    if (hechoInputDTO.getDescripcion() == null || hechoInputDTO.getDescripcion().isBlank()) {
      throw new DatosFaltantes("La descripción es obligatoria.");
    }
    if (hechoInputDTO.getIdCategoria() == null) {
      throw new DatosFaltantes("La categoría es obligatoria.");
    }
    if (hechoInputDTO.getFechaAcontecimiento() == null) {
      throw new DatosFaltantes("La fecha de acontecimiento es obligatoria.");
    }
    if (hechoInputDTO.getFechaAcontecimiento().atStartOfDay().isAfter(LocalDateTime.now())) {
      throw new DatosFaltantes("La fecha de acontecimiento no puede ser futura.");
    }
    if (hechoInputDTO.getLatitud() == null || hechoInputDTO.getLongitud() == null) {
      throw new DatosFaltantes("Debe proporcionar tanto latitud como longitud para la ubicación.");
    }
  }

  private void actualizarCamposHecho(Hecho hechoExistente, HechoInputDTO hechoNuevo) {
    hechoExistente.setTitulo(hechoNuevo.getTitulo());
    hechoExistente.setDescripcion(hechoNuevo.getDescripcion());

    if (!hechoNuevo.getIdCategoria().equals(hechoExistente.getCategoria().getIdCategoria())) {
      Categoria categoria = normalizadorAdapter.obtenerCategoria(hechoNuevo.getIdCategoria()).block();
      hechoExistente.setCategoria(categoria);
    }

    hechoExistente.setFechaAcontecimiento(hechoNuevo.getFechaAcontecimiento().atStartOfDay());

    List<ContenidoMultimedia> contenidoMultimedia =
        this.contenidoMultimediaService.editar(hechoNuevo.getUrlsContenidoMultimedia(), hechoExistente);
    hechoExistente.setMultimedia(contenidoMultimedia);

    actualizarUbicacion(hechoExistente, hechoNuevo);

    hechoExistente.setFechaEdicion(LocalDateTime.now());
  }

  private void actualizarUbicacion(Hecho hechoExistente, HechoInputDTO hechoNuevo) {
    if (hechoExistente.getUbicacion().getLatitud() != hechoNuevo.getLatitud() ||
        hechoExistente.getUbicacion().getLongitud() != hechoNuevo.getLongitud()) {

      Ubicacion ubicacion = ubicacionNormalizada(hechoNuevo);

      hechoExistente.setUbicacion(ubicacion);
    }
  }

  private Ubicacion ubicacionNormalizada(HechoInputDTO hechoInputDTO) {
    Ubicacion ubicacion = normalizadorAdapter.obtenerUbicacionNormalizada(
            hechoInputDTO.getLatitud(),
            hechoInputDTO.getLongitud())
        .block();

    return ubicacion;
  }

  private Hecho obtenerHechoANormalizar(HechoInputDTO hechoInputDTO) {
    Hecho hecho = MapperDeHechos.hechoFromInputDTO(hechoInputDTO);
    Categoria categoria = normalizadorAdapter.obtenerCategoria(hechoInputDTO.getIdCategoria()).block();
    hecho.setCategoria(categoria);

    return hecho;
  }

  private void validarEdicion(Hecho hecho) {
    if (ChronoUnit.DAYS.between(hecho.getFechaCarga(), LocalDateTime.now()) > 7) {
      throw new HechoNoEditable("El hecho con id: " + hecho.getIdHecho() + " no puede ser editado después de 7 días desde su carga.");
    }
  }

  private void detectarSpamHechos(HechoInputDTO hechoInputDTO) {
    boolean existe = hechoRepository.existsByTituloAndDescripcion(
            hechoInputDTO.getTitulo(),
            hechoInputDTO.getDescripcion()
    );

    if (existe) {
      throw new RuntimeException("Hecho duplicado: se considera spam");
    }
  }

  private String obtenerAutorActual() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null
            && authentication.isAuthenticated()
            && !authentication.getPrincipal().equals("anonymousUser")) {
      System.out.println("Autenticando usuario: " + authentication.getName());
      return authentication.getName();
    }

    return null;
  }
}