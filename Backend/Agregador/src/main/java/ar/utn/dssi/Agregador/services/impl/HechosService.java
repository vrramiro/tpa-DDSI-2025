package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.Ubicacion;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.mappers.MapperDeHechos;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import ar.utn.dssi.Agregador.services.IFuentesService;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class HechosService implements IHechosService {
    @Autowired
    private IHechosRepository hechosRepository;

    @Autowired
    private IColeccionRepository coleccionRepository;

    @Autowired
    private IFuentesService fuentesService;

    @Override
    public HechoOutputDTO obtenerHechoPorId(Long idHecho) {
        try {
            Hecho hecho = hechosRepository.findById(idHecho).orElseThrow(IllegalArgumentException::new);

            return MapperDeHechos.hechoOutputDTO(hecho);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el hecho por id: " + e.getMessage(), e);
        }
    }

    @Override
    public List<HechoOutputDTO> obtenerHechos() {
        try {
            return this.hechosRepository
                .findAll()
                .stream()
                .map(MapperDeHechos::hechoOutputDTO)
                .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los hechos: " + e.getMessage(), e);
        }
    }

    @Override
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

            //TODO: ACA NORMALIZAR LOS HECHOS..

            List<Coleccion> colecciones = coleccionRepository.findAll();

            colecciones.parallelStream().forEach(coleccion -> coleccion.agregarHechos(hechosNuevos)); //trabaja varias colecciones por core

            coleccionRepository.saveAll(colecciones);
        } catch (Exception e) {
            throw new RuntimeException("Error al importar los hechos: " + e.getMessage(), e);
        }
    }
}