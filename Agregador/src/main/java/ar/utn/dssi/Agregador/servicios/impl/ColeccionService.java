package ar.utn.dssi.Agregador.servicios.impl;

import ar.utn.dssi.Agregador.modelos.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.modelos.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.modelos.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Coleccion;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;
import ar.utn.dssi.Agregador.modelos.repositorio.IcoleccionRepository;
import ar.utn.dssi.Agregador.servicios.IColeccionService;
import ar.utn.dssi.Agregador.servicios.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColeccionService implements IColeccionService {

    @Autowired
    private IcoleccionRepository coleccionRepository;

    @Autowired
    private IHechosService hechosService;

    @Override
    public void crearColeccion(ColeccionInputDTO coleccionInputDTO) {
        var coleccion = new Coleccion();

        coleccion.setTitulo(coleccionInputDTO.getTitulo());
        coleccion.setDescripcion(coleccionInputDTO.getDescripcion());
        coleccion.setHandle(coleccionInputDTO.getHandle());
        coleccion.setCriteriosDePertenecias(coleccionInputDTO.getCriteriosDePertenecias());

        coleccionRepository.save(coleccion);
    }

    @Override
    public List<ColeccionOutputDTO> obtenerColecciones() {
        var colecciones = coleccionRepository.findall();

        return colecciones
                .stream()
                .map(this::coleccionOutputDTO)
                .toList();
    }


    @Override
    public void guardarEnColeccion(Hecho hecho) {
        var colecciones = coleccionRepository.findall();
        colecciones.forEach(coleccion -> {
            boolean cumpleCriterios = coleccion.getCriteriosDePertenecias().stream()
                    .allMatch(criterio -> criterio.hechoLoCumple(hecho));
            if (cumpleCriterios) {
                coleccion.getHechos().add(hecho);
                coleccionRepository.save(coleccion);
            }
        });
    }

    @Override
    public List<HechoOutputDTO> obtenerHechosDeColeccion(String handle) {
        var coleccion = coleccionRepository.findByHandle(handle);
        var hechosColeccion = coleccion.getHechos();
        return hechosColeccion.stream().map(hechosService::hechoOutputDTO).toList();
    }

    @Override
    public ColeccionOutputDTO coleccionOutputDTO(Coleccion coleccion) {
        var coleccionDto = new ColeccionOutputDTO();

        coleccionDto.setTitulo(coleccion.getTitulo());
        coleccionDto.setDescripcion(coleccion.getDescripcion());
        coleccionDto.setHechos(coleccion.getHechos());
        
        return coleccionDto;
    }


}

