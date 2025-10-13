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
import java.time.LocalDateTime;
import java.util.List;

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
  public List<HechoOutputDTO> obtenerHechos(LocalDateTime fechaReporteDesde, LocalDateTime fechaReporteHasta, LocalDateTime fechaAcontecimientoDesde, LocalDateTime fechaAcontecimientoHasta, String ciudad, String provincia, Long idHecho) {
    try {
      return this.hechosRepository
          .filtrarHechos(fechaReporteDesde, fechaReporteHasta, fechaAcontecimientoDesde, fechaAcontecimientoHasta, ciudad, provincia, idHecho)
          .stream()
          .filter(Hecho::getVisible) // Filtrar solo los hechos visibles
          .map(MapperDeHechos::hechoToOutputDTO)
          .toList();
    } catch (Exception e) {
      throw new RuntimeException("Error al obtener los hechos: " + e.getMessage(), e);
    }
  }

  @Override
  @Transactional
  public void eliminarHecho(Long IdHecho) {
    //TODO revisar gestion de eliminacion en fuente si es estatica o dinamica => ver que fuente es y mandarle que lo elimine
    Hecho hecho = this.hechosRepository.findById(IdHecho).orElseThrow(() -> new HechoNoEcontrado("No se encontro el hecho con id: " + IdHecho));
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