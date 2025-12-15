package ar.utn.dssi.Estadisticas.services.impl;

import ar.utn.dssi.Estadisticas.dto.output.EstadisticaOutputDTO;
import ar.utn.dssi.Estadisticas.mappers.MapperDeEstadisticas;
import ar.utn.dssi.Estadisticas.models.entities.Estadistica;
import ar.utn.dssi.Estadisticas.models.entities.TipoArchivo;
import ar.utn.dssi.Estadisticas.models.entities.TipoEstadistica;
import ar.utn.dssi.Estadisticas.models.entities.data.ContextoDeCalculo;
import ar.utn.dssi.Estadisticas.models.entities.exportador.IExportadorArchivos;
import ar.utn.dssi.Estadisticas.models.entities.exportador.impl.ExportadorFactory;
import ar.utn.dssi.Estadisticas.models.entities.generadorDeEstadisticas.IGeneradorDeEstadisticas;
import ar.utn.dssi.Estadisticas.models.repositories.IEstadisticasRepository;
import ar.utn.dssi.Estadisticas.services.IContextoDeCalculoService;
import ar.utn.dssi.Estadisticas.services.IEstadisticasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstadisticasService implements IEstadisticasService {
  private final IEstadisticasRepository estadisticasRepository;
  private final IContextoDeCalculoService contextoDeCalculoService;
  private final IGeneradorDeEstadisticas generadorDeEstadisticas;

  @Override
  public void calcularEstadisticas() {
    try {
      log.info("Iniciando cálculo de estadísticas...");
      ContextoDeCalculo contextoDeCalculo = contextoDeCalculoService.obtenerContextoDeCalculo();

      List<Estadistica> estadisticasCalculadas = generadorDeEstadisticas.generarEstadisticas(contextoDeCalculo);

      estadisticasRepository.saveAll(estadisticasCalculadas);
      log.info("Cálculo finalizado. Se guardaron {} estadísticas.", estadisticasCalculadas.size());
    } catch (Exception e) {
      log.error("Error crítico al calcular estadísticas", e);
      //throw new RuntimeException(e);
    }
  }

  @Override
  public EstadisticaOutputDTO getProvinciasConMasHechosColeccion(String handle) {
    try {
      Estadistica estadistica = estadisticasRepository
              .findTopByColeccionHandleAndTipoOrderByFechaDeCalculoDesc(
                  handle,
                  TipoEstadistica.COLECCION_PROVINCIA_HECHOS);

      if (estadistica == null) return null;

      return MapperDeEstadisticas.estadisticaOutputDTO(estadistica);
    } catch (Exception e) {
      log.error("Error al obtener provincias por colección", e);
      return null;
    }
  }

  @Override
  public EstadisticaOutputDTO getCategoriaConMasHechos() {
    try {
      Estadistica estadistica = estadisticasRepository
          .findTopByTipoOrderByFechaDeCalculoDesc(TipoEstadistica.CATEGORIA_MAS_HECHOS);

      if (estadistica == null) return null;

      return MapperDeEstadisticas.estadisticaOutputDTO(estadistica);
    } catch (Exception e) {
      log.error("Error al obtener categoría con más hechos", e);
      return null;
    }
  }

  @Override
  public EstadisticaOutputDTO getProvinciasConMasHechoCategoria(Long idCategoria) {
    try {
      Estadistica estadistica = estadisticasRepository
          .findTopByCategoriaIdAndTipoOrderByFechaDeCalculoDesc(
                  idCategoria,
                  TipoEstadistica.CATEGORIA_PROVINCIA_HECHOS);

      if (estadistica == null) return null;

      return MapperDeEstadisticas.estadisticaOutputDTO(estadistica);
    } catch (Exception e) {
      log.error("Error al obtener provincia por categoría", e);
      return null;
    }
  }

  @Override
  public EstadisticaOutputDTO getHorasConMasHechosCategoria(Long idCategoria) {
    try {
      Estadistica estadistica = estadisticasRepository
          .findTopByCategoriaIdAndTipoOrderByFechaDeCalculoDesc(
                  idCategoria,
                  TipoEstadistica.CATEGORIA_HORA_HECHOS);

      if (estadistica == null) return null;

      return MapperDeEstadisticas.estadisticaOutputDTO(estadistica);
    } catch (Exception e) {
      log.error("Error al obtener horas por categoría", e);
      return null;
    }
  }

  @Override
  public EstadisticaOutputDTO getCantidadSpam() {
    try {
      Estadistica estadistica = estadisticasRepository
          .findTopByTipoOrderByFechaDeCalculoDesc(TipoEstadistica.SOLICITUD_SPAM);

      if (estadistica == null) return null;

      return MapperDeEstadisticas.estadisticaOutputDTO(estadistica);
    } catch (Exception e) {
      log.error("Error al obtener cantidad de spam", e);
      return null;
    }
  }

  @Override
  public File getArchivoEstadisticas(TipoArchivo tipo) {
    try {
      IExportadorArchivos exportador = ExportadorFactory.getExportador(tipo);
      return exportador.exportarEstadisticas();
    } catch (Exception e) {
      log.error("Error al exportar archivo", e);
      return null;
    }
}
}