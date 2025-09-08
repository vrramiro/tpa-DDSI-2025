package ar.utb.ba.dsi.estadisticas.services.impl;

import ar.utb.ba.dsi.estadisticas.models.DTOs.outputDTOs.EstadisticaOutputDTO;
import ar.utb.ba.dsi.estadisticas.models.entities.DatosDeCalculo;
import ar.utb.ba.dsi.estadisticas.models.entities.Estadistica;
import ar.utb.ba.dsi.estadisticas.models.entities.calculadores.IGeneradorDeEstadisticas;
import ar.utb.ba.dsi.estadisticas.models.entities.exportador.IExportadorArchivos;
import ar.utb.ba.dsi.estadisticas.models.entities.exportador.impl.ExportadorFactory;
import ar.utb.ba.dsi.estadisticas.models.entities.TipoArchivo;
import ar.utb.ba.dsi.estadisticas.models.entities.TipoEstadistica;
import ar.utb.ba.dsi.estadisticas.models.mappers.MapperDeEstadisticas;
import ar.utb.ba.dsi.estadisticas.models.repositories.IEstadisticasRepository;
import ar.utb.ba.dsi.estadisticas.services.IDatosDeCaluculoService;
import ar.utb.ba.dsi.estadisticas.services.IEstadisticasService;
import ar.utb.ba.dsi.estadisticas.models.errores.ErrorAlCalcular;
import org.springframework.stereotype.Service;


import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadisticasService implements IEstadisticasService {
    private final IEstadisticasRepository estadisticasRepository;
    private final IDatosDeCaluculoService datosDeCalculoService;
    private final List<IGeneradorDeEstadisticas> generadoresDeEstadisticas;

    public EstadisticasService(IEstadisticasRepository estadisticasRepository, IDatosDeCaluculoService datosDeCalculoService, List<IGeneradorDeEstadisticas> generadoresDeEstadisticas) {
        this.estadisticasRepository = estadisticasRepository;
        this.datosDeCalculoService = datosDeCalculoService;
        this.generadoresDeEstadisticas = generadoresDeEstadisticas;
    }

    @Override
    public void calcularEstadisticas() {
        try {
            DatosDeCalculo datosDeCalculo = datosDeCalculoService.obtenerDatosDeCalculo();

            List<Estadistica> estadisticasCalculadas = generadoresDeEstadisticas.stream()
                    .flatMap(generador -> generador.generarEstadistica(datosDeCalculo).stream())
                    .toList();

            estadisticasRepository.saveAll(estadisticasCalculadas);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public EstadisticaOutputDTO getProvinciasConMasHechosColeccion(Long idColeccion) {
        try {
            Estadistica estadistica = estadisticasRepository
                    .findEStadisticaByColeccionIdAndTipoAndFechaDeCalculoIsAfter
                            (idColeccion,
                            TipoEstadistica.COLECCION_PROVINCIA_HECHOS,
                            LocalDateTime.now());
            return MapperDeEstadisticas.estadisticaOutputDTO(estadistica);
        } catch (Exception e) {
            throw new ErrorAlCalcular("Error al calcular provincias con mas hechos en una coleccion.");
        }
    }

    @Override
    public EstadisticaOutputDTO getCategoriaConMasHechos(){
        try {
            Estadistica estadistica = estadisticasRepository
                    .findEstadisticaByTipoAndFechaDeCalculoIsAfter
                            (TipoEstadistica.CATEGORIA_MAS_HECHOS,
                            LocalDateTime.now());
            return MapperDeEstadisticas.estadisticaOutputDTO(estadistica);
        } catch (Exception e) {
            throw new ErrorAlCalcular("Error al calcular categorias con mas hechos en una coleccion.");
        }
}

    @Override
    public EstadisticaOutputDTO getProvinciasConMasHechoCategoria(Long idCategoria){
        try {
            Estadistica estadistica = estadisticasRepository
                    .findEstadisticaByCategoriaIdAndTipoAndFechaDeCalculoIsAfter
                            (idCategoria, TipoEstadistica.
                                CATEGORIA_PROVINCIA_HECHOS,
                                LocalDateTime.now());

            return MapperDeEstadisticas.estadisticaOutputDTO(estadistica);
        } catch (Exception e) {
            throw new ErrorAlCalcular("Error al calcular categorias con mas hechos en una coleccion.");
        }
    }

    @Override
    public EstadisticaOutputDTO getHorasConMasHechosCategoria(Long idCategoria){
        try {
            Estadistica estadistica =  estadisticasRepository
                    .findEstadisticaByCategoriaIdAndTipoAndFechaDeCalculoIsAfter
                            (idCategoria,
                            TipoEstadistica.CATEGORIA_HORA_HECHOS,
                            LocalDateTime.now());
            return MapperDeEstadisticas.estadisticaOutputDTO(estadistica);
        } catch (Exception e) {
            throw new ErrorAlCalcular("Error al calcular horas con mas hechos en una categorias.");
        }
    }

    @Override
    public EstadisticaOutputDTO getCantidadSpam(){
        try{
            Estadistica estadistica = estadisticasRepository
            .findEstadisticaByTipoAndFechaDeCalculoIsAfter(TipoEstadistica.SOLICITUD_SPAM,
                    LocalDateTime.now());
            return MapperDeEstadisticas.estadisticaOutputDTO(estadistica);
        } catch ( Exception e ) {
            throw new ErrorAlCalcular("Error al calcular cantidad de spam en las solicitudes de eliminacion.");
        }
    }

    @Override
    public File getArchivoEstadisticas(TipoArchivo tipo) {
        IExportadorArchivos exportador = ExportadorFactory.getExportador(tipo);
        return exportador.exportarEstadisticas();
    }
}
