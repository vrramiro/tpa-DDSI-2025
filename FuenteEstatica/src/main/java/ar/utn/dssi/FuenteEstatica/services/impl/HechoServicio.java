package ar.utn.dssi.FuenteEstatica.services.impl;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import ar.utn.dssi.FuenteEstatica.services.IHechoServicio;
import ar.utn.dssi.FuenteEstatica.models.repositories.IHechosRepositorio;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.impl.FactoryLector;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.ILectorDeArchivos;
import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.io.File;

@Service
public class HechoServicio implements IHechoServicio {

    @Autowired
    private IHechosRepositorio hechoRepositorio;

    @Override
    //TODO ver si hay que devolver una lista o nada
    public List<HechoOutputDTO> extraerHechos(File archivo) {
        //TODO: Se extrae hecho del archivo y carga en el repositorio (Nico)
        ILectorDeArchivos lectorDeArchivos = FactoryLector.crearLector(archivo);
        List<Hecho> hechos = lectorDeArchivos.importarHechos(archivo);
        List<HechoOutputDTO> hechosDTO = hechos.stream()
                                                .map(this :: hechoOutputDTO)
                                                .toList();
        hechoRepositorio.save(hechos);
        return hechosDTO;
    }

    @Override
    public List<HechoOutputDTO> obtenerHechos() {
        //TODO: Obtiene los hechos del repositorio (Nico)
        //obtengo hechos del repositorio tranformarlos en DTO y mandarlo al controler
        return this.hechoRepositorio
                .findAll()
                .stream()
                .map(this::hechoOutputDTO)
                .toList();
    }

    private  HechoOutputDTO hechoOutputDTO(Hecho hecho) {
        HechoOutputDTO hechoOutputDTO = new HechoOutputDTO();
        hechoOutputDTO.setId(hecho.getId());
        hechoOutputDTO.setTitulo(hecho.getTitulo());
        hechoOutputDTO.setDescripcion(hecho.getDescripcion());
        // TODO hechoOutputDTO.setIdCategoria(hecho.getCategoria());
        hechoOutputDTO.setUbicacion(hecho.getUbicacion());
        hechoOutputDTO.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        hechoOutputDTO.setFechaCarga(hecho.getFechaCarga());
        return hechoOutputDTO;
    }
}
