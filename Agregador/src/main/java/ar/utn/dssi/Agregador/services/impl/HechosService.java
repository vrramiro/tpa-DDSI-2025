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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
                                            String provincia) {
    try {
      List<Hecho> hechos = this.hechosRepository.findHechosByVisibleTrueAndFiltrados(
          fechaReporteDesde,
          fechaReporteHasta,
          fechaAcontecimientoDesde,
          fechaAcontecimientoHasta,
          idCategoria,
          provincia
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
      System.out.println("HECHOS TRAIDOS: " + hechosNuevos.size());

      colecciones.parallelStream().forEach(coleccion -> coleccion.agregarHechos(hechosNuevos));

      coleccionRepository.saveAll(colecciones);
    } catch (Exception e) {
      throw new RuntimeException("Error al importar los hechos: " + e.getMessage(), e);
    }
  }
}