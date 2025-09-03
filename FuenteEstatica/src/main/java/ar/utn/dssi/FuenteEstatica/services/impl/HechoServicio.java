package ar.utn.dssi.FuenteEstatica.services.impl;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import ar.utn.dssi.FuenteEstatica.models.errores.ErrorActualizarRepositorio;
import ar.utn.dssi.FuenteEstatica.models.errores.ErrorGeneralRepositorio;
import ar.utn.dssi.FuenteEstatica.models.errores.RepositorioVacio;
import ar.utn.dssi.FuenteEstatica.models.errores.ValidacionException;
import ar.utn.dssi.FuenteEstatica.services.IHechoServicio;
import ar.utn.dssi.FuenteEstatica.models.repositories.IHechosRepositorio;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.impl.FactoryLector;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.ILectorDeArchivos;
import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.NoSuchElementException;

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

        if(hechos.size() <= cantidadMinimaDeHechos){
            throw new ValidacionException("El archivo no cumple con la cantidad de minima de hechos:" + cantidadMinimaDeHechos);
        }

        hechoRepositorio.save(hechos);
    }


    @Override
    public List<HechoOutputDTO> obtenerHechos() {
        var hechos = this.hechoRepositorio.findAll();

        if (hechos.isEmpty()) {
            throw new RepositorioVacio("El repositorio esta vacio, no tiene datos.");

        }

        var hechosAEnviar = hechos.stream().map(this::hechoOutputDTO).toList();

        var hechosNuevos = hechos.stream().filter(hecho-> hecho.getEnviado().equals(false));

        List<String> errores = new ArrayList<>();

        hechosNuevos.
                forEach(hecho -> {
                        try {
                            if(hecho.getId() == null) {
                                errores.add(hecho.getTitulo());
                            } else {
                                hecho.setEnviado(true);
                                hechoRepositorio.update(hecho);
                            }
                        } catch (Exception e) {
                            throw new ErrorGeneralRepositorio("Error en el repositorio");
                        }
                }
        );

        if(!errores.isEmpty()){
            throw new ErrorActualizarRepositorio(
                    "No se pudo actualizar los hechos: " + String.join(", ", errores),
                    HttpStatus.NOT_FOUND
            );
        }

        return hechosAEnviar;
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
