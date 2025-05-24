package ar.utn.dssi.FuenteEstatica.services.impl;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import ar.utn.dssi.FuenteEstatica.services.IHechoServicio;
import ar.utn.dssi.FuenteEstatica.models.repositories.IHechosRepositorio;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.impl.FactoryLector;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.ILectorDeArchivos;
import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.io.File;

@Service
public class HechoServicio implements IHechoServicio {

    @Autowired
    private IHechosRepositorio hechoRepositorio;

    @Value("${cantidadMinimaDeHechos}")
    private Integer cantidadMinimaDeHechos;

    @Override
    public void importarArchivo(File archivo) {
        ILectorDeArchivos lectorDeArchivos = FactoryLector.crearLector(archivo);
        List<Hecho> hechos = lectorDeArchivos.importarHechos(archivo);

        if(hechos.size() >= cantidadMinimaDeHechos){
            hechoRepositorio.save(hechos);
        }
        else{
            throw new RuntimeException("El archivo no cumple con la cantidad de minima de hechos.");
        }
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
        var hechoOutputDTO = new HechoOutputDTO();
        hechoOutputDTO.setTitulo(hecho.getTitulo());
        hechoOutputDTO.setDescripcion(hecho.getDescripcion());
        hechoOutputDTO.setIdCategoria(hecho.getCategoria().getIdCategoria());
        hechoOutputDTO.setUbicacion(hecho.getUbicacion());
        hechoOutputDTO.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        hechoOutputDTO.setFechaCarga(hecho.getFechaCarga());
        return hechoOutputDTO;
    }
}
