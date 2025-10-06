package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.mappers.MapperDeHechos;
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
    public HechoOutputDTO obtenerHechoPorId(Long idHecho) {
        try {
            Hecho hecho = hechosRepository.findById(idHecho).orElseThrow(IllegalArgumentException::new);

            return MapperDeHechos.hechoToOutputDTO(hecho);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el hecho por id: " + e.getMessage(), e);
        }
    }



    @Override
    public List<HechoOutputDTO> obtenerHechos(LocalDateTime fechaReporteDesde, LocalDateTime fechaReporteHasta, LocalDateTime fechaAcontecimientoDesde, LocalDateTime fechaAcontecimientoHasta, Double latitud, Double longitud, Long fuenteId) {
        try {
            return this.hechosRepository
                .filtrarHechos(fechaReporteDesde, fechaReporteHasta, fechaAcontecimientoDesde, fechaAcontecimientoHasta, latitud, longitud, fuenteId)
                .stream()
                .map(MapperDeHechos::hechoToOutputDTO)
                .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los hechos: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void eliminarHecho(Long IdHecho) {
        try {
            //TODO revisar gestion de eliminacion en fuente si es estatica o dinamica => ver que fuente es y mandarle que lo elimine
            Hecho hecho = this.hechosRepository.findById(IdHecho).orElseThrow(IllegalArgumentException::new);
            hecho.setVisible(false);
            hechosRepository.save(hecho);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el hecho: " + e.getMessage(), e);
        }
    }

    @Override
    public void importarNuevosHechos() {
        try {
            List<Hecho> hechosNuevos = this.fuentesService.hechosNuevos();

            List<Coleccion> colecciones = coleccionRepository.findAll();

            colecciones.parallelStream().forEach(coleccion -> coleccion.agregarHechos(hechosNuevos)); //trabaja varias colecciones por core

            coleccionRepository.saveAll(colecciones);
        } catch (Exception e) {
            throw new RuntimeException("Error al importar los hechos: " + e.getMessage(), e);
        }
    }
}