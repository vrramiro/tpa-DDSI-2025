package ar.utn.dssi.FuenteEstatica.services.impl;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import ar.utn.dssi.FuenteEstatica.models.entities.normalizadorAdapter.INormalizadorAdapter;
import ar.utn.dssi.FuenteEstatica.models.errores.ErrorActualizarRepositorio;
import ar.utn.dssi.FuenteEstatica.models.errores.ErrorGeneralRepositorio;
import ar.utn.dssi.FuenteEstatica.models.errores.RepositorioVacio;
import ar.utn.dssi.FuenteEstatica.models.errores.ValidacionException;
import ar.utn.dssi.FuenteEstatica.models.mappers.MapperDeHechos;
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

    @Autowired
    private FactoryLector factoryLector;

    //TODO Ver de poner un autowired o una inyeccion por constructor
    private INormalizadorAdapter normalizadorAdapter;

    @Value("${cantidadMinimaDeHechos}")
    private Integer cantidadMinimaDeHechos;


    @Override
    public void importarArchivo(File archivo) {
        ILectorDeArchivos lectorDeArchivos = factoryLector.crearLector(archivo);
        List<Hecho> hechos = lectorDeArchivos.importarHechos(archivo);

        if(hechos.size() <= cantidadMinimaDeHechos){
            throw new ValidacionException("El archivo no cumple con la cantidad de minima de hechos:" + cantidadMinimaDeHechos +", el archivo tiene: " + hechos.size());
        }

        List<Hecho> hechosNormalizados = new ArrayList<>();

        for (Hecho hecho : hechos) {
            try {
                Hecho hechoNormalizado = normalizadorAdapter.obtenerHechoNormalizado(hecho).block(); // si es Mono

                if (hechoNormalizado != null) {
                    hechosNormalizados.add(hechoNormalizado);
                }
            } catch (Exception e) {
                System.err.println("Error normalizando hecho: " + e.getMessage());
            }
        }

        // Guardar hechos ya normalizados
        hechoRepositorio.saveAll(hechosNormalizados);
    }


    @Override
    public List<HechoOutputDTO> obtenerHechos() {
        var hechos = this.hechoRepositorio.findAll();

        if (hechos.isEmpty()) {
            throw new RepositorioVacio("El repositorio esta vacio, no tiene datos.");
        }

        var hechosAEnviar = hechos.stream().map(MapperDeHechos::hechoOutputDTO).toList();
        List<String> errores = actualizacionAEnviado(hechos);

        if(!errores.isEmpty()){
            throw new ErrorActualizarRepositorio(
                    "No se pudo actualizar los hechos: " + String.join(", ", errores),
                    HttpStatus.NOT_FOUND
            );
        }
        return hechosAEnviar;
    }

    private List<String> actualizacionAEnviado(List<Hecho> hechos) {
        var hechosNuevos = hechos.stream().filter(hecho-> hecho.getEnviado().equals(false));

        List<String> errores = new ArrayList<>();
        hechosNuevos.
                forEach(hecho -> {
                        try {
                            if(hecho.getId() == null) {
                                errores.add(hecho.getTitulo());
                            } else {
                                hecho.setEnviado(true);
                                hechoRepositorio.save(hecho);
                            }
                        } catch (Exception e) {
                            throw new ErrorGeneralRepositorio("Error en el repositorio");
                        }
                }
        );
        return errores;
    }


}

