package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.dto.output.HechoOutputDTO;
import ar.utn.dssi.Agregador.error.HechoNoEcontrado;
import ar.utn.dssi.Agregador.mappers.MapperDeHechos;
import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import ar.utn.dssi.Agregador.services.IFuentesService;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HechosService implements IHechosService {
  private final IHechosRepository hechosRepository;
  private final IColeccionRepository coleccionRepository;
  private final IFuentesService fuentesService;

  public HechosService(IHechosRepository hechosRepository, IColeccionRepository coleccionRepository, IFuentesService fuentesService) {
    this.hechosRepository = hechosRepository;
    this.coleccionRepository = coleccionRepository;
    this.fuentesService = fuentesService;
  }

  @Override
  public List<HechoOutputDTO> obtenerHechos(LocalDate fechaReporteDesde,
                                            LocalDate fechaReporteHasta,
                                            LocalDate fechaAcontecimientoDesde,
                                            LocalDate fechaAcontecimientoHasta,
                                            Long idCategoria,
                                            String provincia,
                                            Double latMin, Double latMax, Double lonMin, Double lonMax) {
    try {
      LocalDateTime fechaReporteDesdeDT = (fechaReporteDesde != null) ? fechaReporteDesde.atStartOfDay() : null;
      LocalDateTime fechaReporteHastaDT = (fechaReporteHasta != null) ? fechaReporteHasta.atTime(23, 59, 59) : null;
      LocalDateTime fechaAcontecimientoDesdeDT = (fechaAcontecimientoDesde != null) ? fechaAcontecimientoDesde.atStartOfDay() : null;
      LocalDateTime fechaAcontecimientoHastaDT = (fechaAcontecimientoHasta != null) ? fechaAcontecimientoHasta.atTime(23, 59, 59) : null;

      List<Hecho> hechos = this.hechosRepository.findHechosByVisibleTrueAndFiltrados(
              fechaReporteDesdeDT,
              fechaReporteHastaDT,
              fechaAcontecimientoDesdeDT,
              fechaAcontecimientoHastaDT,
              idCategoria,
              provincia,
              latMin, latMax, lonMin, lonMax
      );

      return hechos.stream()
              .map(MapperDeHechos::hechoToOutputDTO)
              .toList();

    } catch (Exception e) {
      throw new RuntimeException("Error al obtener los hechos: " + e.getMessage(), e);
    }
  }


  @Override
  public HechoOutputDTO obtenerHechoPorId(Long idHecho) {
    Hecho hecho = intentarObtenerHecho(idHecho);
    return MapperDeHechos.hechoToOutputDTO(hecho);
  }

  @Override
  @Transactional
  public List<HechoOutputDTO> obtenerHechosPorAutor(String autor) {
    List<Hecho> hechos = hechosRepository.findByAutor(autor);
    return hechos.stream()
            .map(MapperDeHechos::hechoToOutputDTO)
            .collect(Collectors.toList());
  }

  private Hecho intentarObtenerHecho(Long idHecho) {
    Optional<Hecho> hechoOp = this.hechosRepository.findHechoByIdAndVisible(idHecho, true);

    if (hechoOp.isEmpty()) {
      throw new HechoNoEcontrado("No se encontró hecho con id: " + idHecho);
    }

    return hechoOp.get();
  }

  @Override
  @Transactional
  public void eliminarHecho(Long idHecho) {
    //TODO revisar gestion de eliminacion en fuente si es estatica o dinamica => ver que fuente es y mandarle que lo elimine
    Hecho hecho = this.hechosRepository.findById(idHecho).orElseThrow(() -> new HechoNoEcontrado("No se encontro el hecho con id: " + idHecho));
    hecho.setVisible(false);
    hechosRepository.save(hecho);
  }

  @Override
  @Transactional
  public void importarNuevosHechos() {
    try {
      List<Hecho> hechosNuevos = this.fuentesService.hechosNuevos();
      System.out.println(hechosNuevos);
      hechosRepository.saveAll(hechosNuevos);

      List<Coleccion> colecciones = coleccionRepository.findAll();
      System.out.println("CANTIDAD DE HECHOS TRAIDOS: " + hechosNuevos.size());

      colecciones.stream().forEach(coleccion -> coleccion.agregarHechos(hechosNuevos));

      coleccionRepository.saveAll(colecciones);
    } catch (Exception e) {
      throw new RuntimeException("Error al importar los hechos: " + e.getMessage(), e);
    }
  }

  @Override
  public Page<HechoOutputDTO> obtenerTodos(Pageable pageable) {
    Page<Hecho> paginaHechos = hechosRepository.findByVisibleTrue(pageable);

    return paginaHechos.map(MapperDeHechos::hechoToOutputDTO);
  }

  @Override
  public List<HechoOutputDTO> obtenerHechosRecientes(int limit) {
    List<Hecho> hechos = hechosRepository.findHechosRecientes(PageRequest.of(0, limit));
    return hechos.stream()
            .map(MapperDeHechos::hechoToOutputDTO)
            .collect(Collectors.toList());
  }
}