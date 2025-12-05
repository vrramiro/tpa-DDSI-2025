package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.dto.input.ColeccionInputDTO;
import ar.utn.dssi.Agregador.dto.input.CriterioDePertenenciaInputDTO;
import ar.utn.dssi.Agregador.dto.input.FuenteInputDTO;
import ar.utn.dssi.Agregador.dto.output.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.dto.output.HechoOutputDTO;
import ar.utn.dssi.Agregador.error.ColeccionAguardandoActualizacion;
import ar.utn.dssi.Agregador.error.ColeccionNoEncontrada;
import ar.utn.dssi.Agregador.error.ColeccionTituloDuplicado;
import ar.utn.dssi.Agregador.error.CriterioPorFechasIncorrecto;
import ar.utn.dssi.Agregador.error.DatosDeColeccionFaltantes;
import ar.utn.dssi.Agregador.event.ColeccionDesactualizadaEvent;
import ar.utn.dssi.Agregador.mappers.MapperDeColecciones;
import ar.utn.dssi.Agregador.mappers.MapperDeCriterio;
import ar.utn.dssi.Agregador.mappers.MapperDeHechos;
import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.TipoCriterio;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.fuente.TipoFuente;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.ModoNavegacionFactory;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import ar.utn.dssi.Agregador.services.IColeccionService;
import ar.utn.dssi.Agregador.services.IFuentesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColeccionService implements IColeccionService {
  private final IColeccionRepository coleccionRepository;
  private final IFuentesService fuentesService;
  private final ModoNavegacionFactory modoNavegacionFactory;

  private final ApplicationEventPublisher publicador;

  @Override
  @Transactional
  public ColeccionOutputDTO crearColeccion(ColeccionInputDTO input) {

    filtrarCriteriosVacios(input);

    validarDatosBasicos(input);
    validarCriterios(input);
    validarDuplicadoPorTitulo(input.getTitulo());

    Coleccion coleccion = MapperDeColecciones.coleccionFromColeccionInputDTO(input);
    coleccion.setFuentes(fuentesNuevas(input));

    coleccion = coleccionRepository.save(coleccion);

    publicador.publishEvent(new ColeccionDesactualizadaEvent(coleccion.getHandle()));

    return MapperDeColecciones.coleccionOutputDTOFromColeccion(coleccion);
  }

  @Override
  @Transactional
  public Page<ColeccionOutputDTO> obtenerColecciones(Pageable pageable) {
    Page<Coleccion> colecciones = this.obtenerColeccionesActualizadas(pageable);

    return colecciones.map(MapperDeColecciones::coleccionOutputDTOFromColeccion);
  }

  @Override
  @Transactional
  public ColeccionOutputDTO editarColeccion(String handle, ColeccionInputDTO input) {
    validarDatosBasicos(input);
    validarCriterios(input);

    Coleccion coleccion = obtenerColeccionSiExiste(handle);

    if (!coleccion.getActualizada())
      throw new ColeccionAguardandoActualizacion("La coleccion ya fue modificada, aguarde a que se actualice.");

    if (!coleccion.getTitulo().equals(input.getTitulo()))
      validarDuplicadoPorTitulo(input.getTitulo());

    if (!validarCambioCriterios(coleccion, input) && !validarCambioFuentes(coleccion, input)) {
      coleccion.setTitulo(input.getTitulo());
      coleccion.setDescripcion(input.getDescripcion());
      intentarGuardarColeccion(coleccion);
      return MapperDeColecciones.coleccionOutputDTOFromColeccion(coleccion);
    }

    Coleccion coleccionEditada = MapperDeColecciones.coleccionFromColeccionInputDTO(input);

    // Para que el ORM actualice el registro con el save y se haga la actualizacion gracias a las cascadas y orphanRemoval
    coleccionEditada.setHandle(handle);
    coleccionEditada.setFuentes(fuentesNuevas(input));

    // Los hechos se pierden al editar la coleccion, dado que deben ser recalculados
    intentarGuardarColeccion(coleccionEditada);

    if (!coleccion.getActualizada()) publicador.publishEvent(new ColeccionDesactualizadaEvent(coleccion.getHandle()));

    return MapperDeColecciones.coleccionOutputDTOFromColeccion(coleccion);
  }

  @Override
  public void eliminarColeccion(String handle) {
    Coleccion coleccion = obtenerColeccionSiExiste(handle);

    coleccionRepository.delete(coleccion);
  }

  @Override
  public Page<HechoOutputDTO> obtenerHechosDeColeccion(
          String modoNavegacion,
          String handle,
          LocalDate fechaReporteDesde,
          LocalDate fechaReporteHasta,
          LocalDate fechaAcontecimientoDesde,
          LocalDate fechaAcontecimientoHasta,
          String provincia,
          String ciudad,
          Pageable pageable // <-- Parámetro recibido
  ) {
    Coleccion coleccion = this.coleccionRepository.findColeccionByHandle(handle)
            .orElseThrow(() -> new ColeccionNoEncontrada(handle));

    if (!Boolean.TRUE.equals(coleccion.getActualizada()))
      throw new ColeccionAguardandoActualizacion("La colección no esta disponible para navegación.");

    IModoNavegacion modo = modoNavegacionFactory.modoDeNavegacionFromString(modoNavegacion);

    Page<Hecho> hechosPage = coleccionRepository.filtrarHechosDeColeccion(
            handle, fechaReporteDesde, fechaReporteHasta,
            fechaAcontecimientoDesde, fechaAcontecimientoHasta,
            ciudad, provincia, pageable
    );


    return hechosPage.map(MapperDeHechos::hechoToOutputDTO);
  }


  @Override
  public ColeccionOutputDTO obtenerColeccion(String handle) {
    Coleccion coleccion = this.coleccionRepository.findColeccionByHandle(handle)
            .orElseThrow(() -> new ColeccionNoEncontrada(handle));

    return MapperDeColecciones.coleccionOutputDTOFromColeccion(coleccion);
  }

  private void validarDatosBasicos(ColeccionInputDTO input) {
    if (input.getTitulo() == null || input.getTitulo().isEmpty())
      throw new DatosDeColeccionFaltantes("El titulo es obligatorio.");

    if (input.getDescripcion() == null || input.getDescripcion().isEmpty())
      throw new DatosDeColeccionFaltantes("La descripcion es obligatoria.");

    if (input.getCriteriosDePertenecias() == null || input.getCriteriosDePertenecias().isEmpty())
      throw new DatosDeColeccionFaltantes("Debe tener al menos un criterio de pertenencia.");

    if (input.getFuentes() == null || input.getFuentes().isEmpty())
      throw new DatosDeColeccionFaltantes("Debe tener al menos una fuente.");

    if (input.getConsenso() == null)
      throw new DatosDeColeccionFaltantes("El algoritmo de consenso es obligatorio.");
  }

  private Coleccion obtenerColeccionSiExiste(String handle) {
    return coleccionRepository.findColeccionByHandle(handle).orElseThrow(() -> new ColeccionNoEncontrada(handle));
  }

  private Page<Coleccion> obtenerColeccionesActualizadas(Pageable pageable) {
    return coleccionRepository.findColeccionByActualizada(pageable, true);
  }

  private boolean validarCambioCriterios(Coleccion coleccion, ColeccionInputDTO input) {
    Map<TipoCriterio, String> datosCriteriosNuevos = input.getCriteriosDePertenecias().stream()
        .collect(Collectors
            .toMap(CriterioDePertenenciaInputDTO::getTipo,
                CriterioDePertenenciaInputDTO::getValor));

    Map<TipoCriterio, String> datosCriteriosActuales = coleccion.getCriterios().stream()
        .collect(Collectors
            .toMap(CriterioDePertenencia::getTipoCriterio,
                CriterioDePertenencia::getValor));

    return !datosCriteriosNuevos.equals(datosCriteriosActuales);
  }

  private boolean validarCambioFuentes(Coleccion coleccion, ColeccionInputDTO input) {
    Set<TipoFuente> tiposDeFuentesInput = input.getFuentes().stream()
        .map(FuenteInputDTO::getTipoFuente)
        .collect(Collectors.toSet());

    Set<TipoFuente> tiposDeFuentesActuales = coleccion.getFuentes().stream()
        .map(f -> f.getTipoFuente().getTipoFuente())
        .collect(Collectors.toSet());

    return !tiposDeFuentesInput.equals(tiposDeFuentesActuales);
  }

  private void validarDuplicadoPorTitulo(String titulo) {
    Optional<Coleccion> coleccionDuplicada = coleccionRepository.findColeccionByTitulo(titulo);
    if (coleccionDuplicada.isPresent())
      throw new ColeccionTituloDuplicado(titulo);
  }

  private void validarCriterios(ColeccionInputDTO input) {
    Map<TipoCriterio, String> criterios = new java.util.HashMap<>();

    for (CriterioDePertenenciaInputDTO c : input.getCriteriosDePertenecias()) {
      if (c.getTipo() == null || c.getValor() == null || c.getValor().trim().isEmpty()) {
        continue;
      }

      if (criterios.containsKey(c.getTipo())) {
        throw new DatosDeColeccionFaltantes("No puede haber más de un criterio del mismo tipo (" + c.getTipo() + ").");
      }

      criterios.put(c.getTipo(), c.getValor());
    }

    if (criterios.containsKey(TipoCriterio.FECHA_DESDE)) {
      LocalDate fechaDesde = MapperDeCriterio.parsearFecha(criterios.get(TipoCriterio.FECHA_DESDE));
      if (fechaDesde.isAfter(LocalDate.now())) {
        throw new CriterioPorFechasIncorrecto("El criterio 'fecha desde' no puede ser una fecha futura.");
      }
    }
  }

  private List<Fuente> fuentesNuevas(ColeccionInputDTO input) {
    List<String> tiposDeFuentes = input.getFuentes().stream()
            .map(f -> f.getTipoFuente().toString())
            .collect(Collectors.toList());

    return fuentesService.obtenerFuentesPorTiposDeFuente(tiposDeFuentes);
  }

  private void intentarGuardarColeccion(Coleccion coleccion) {
    try {
      coleccionRepository.save(coleccion);
    } catch (Exception e) {
      log.error("Error al guardar la coleccion: {}", e.getMessage());
      throw e;
    }
  }

  private void filtrarCriteriosVacios(ColeccionInputDTO input) {
    if (input.getCriteriosDePertenecias() != null) {
      input.setCriteriosDePertenecias(
              input.getCriteriosDePertenecias().stream()
                      .filter(c -> c.getValor() != null && !c.getValor().trim().isEmpty())
                      .collect(Collectors.toList())
      );
    }
  }
}

