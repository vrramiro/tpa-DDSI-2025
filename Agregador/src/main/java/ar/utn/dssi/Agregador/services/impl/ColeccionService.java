package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Coleccion;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import ar.utn.dssi.Agregador.models.entities.criterio.ICriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import ar.utn.dssi.Agregador.services.IColeccionService;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ColeccionService implements IColeccionService {
    @Autowired
    private IColeccionRepository coleccionRepository;

    @Autowired
    private IHechosRepository hechosRepositorio;

    @Autowired
    private IHechosService hechosService;

    @Autowired
    private FuentesService fuentesService;

    @Override
    public void crearColeccion(ColeccionInputDTO coleccionInputDTO) {
        var coleccion = new Coleccion();

        coleccion.setTitulo(coleccionInputDTO.getTitulo());
        coleccion.setDescripcion(coleccionInputDTO.getDescripcion());
        coleccion.setHandle(coleccionInputDTO.getHandle());
        coleccion.setCriteriosDePertenecias(coleccionInputDTO.getCriteriosDePertenecias());

        coleccionRepository.save(coleccion);
        //TODO deberia devolver la coleccion creada => falta manejo de errores
    }

    @Override
    public List<ColeccionOutputDTO> obtenerColecciones() {
        var colecciones = coleccionRepository.findall();

        return colecciones
                .stream()
                .map(this::coleccionOutputDTO)
                .toList();
    }

    //Llega el identificador de la coleccion desde el controller
    @Override
    public List<HechoOutputDTO> obtenerHechosDeColeccion(String handle) {
        var coleccion = coleccionRepository.findByHandle(handle);
        var hechosColeccion = coleccion.getHechos();

        return hechosColeccion.stream().map(hechosService::hechoOutputDTO).toList();
    }

    @Override
    public Mono<Void> refrescarColecciones(Hecho hecho){
        return Flux
            .fromIterable(coleccionRepository.findall())
            .flatMap(coleccion -> {

                this.guardarEnColeccion(coleccion, hecho);

                return Mono.empty();
            })
            .then();
    }

    private void guardarEnColeccion(Coleccion coleccion, Hecho hecho) {
        boolean cumpleCriterios = coleccion
            .getCriteriosDePertenecias()
            .stream()
            .allMatch(criterio -> criterio.hechoLoCumple(hecho));

        if (cumpleCriterios) {
            coleccion.getHechos().add(hecho);
            coleccionRepository.update(coleccion);
        }
    }

    private ColeccionOutputDTO coleccionOutputDTO(Coleccion coleccion) {
        var coleccionDto = new ColeccionOutputDTO();

        coleccionDto.setTitulo(coleccion.getTitulo());
        coleccionDto.setDescripcion(coleccion.getDescripcion());
        coleccionDto.setHechos(coleccion.getHechos());

        return coleccionDto;
    }

    @Override
    public void agregarCriterioDePertenencia(ICriterioDePertenencia nuevoCriterio, String handle) {
        Coleccion coleccion = coleccionRepository.findByHandle(handle);
        coleccion.getCriteriosDePertenecias().add(nuevoCriterio);

        refrescarHechosEnColeccion(coleccion)
            .then(Mono.fromRunnable(() -> coleccionRepository.update(coleccion)))
            .subscribe();
    }

    private Mono<Void> refrescarHechosEnColeccion(Coleccion coleccion){
        return Flux
            .fromIterable(hechosRepositorio.findall())
            .flatMap(hecho -> {
                this.guardarEnColeccion(coleccion, hecho);
                return Mono.empty();
            })
            .then();
    }

    private void agregarFuente(String handle, Long idFuenteOrigen)
    {
        Fuente fuenteAAgregar = fuentesService.obtenerFuentePorId(idFuenteOrigen);
        Coleccion coleccionAModificar = coleccionRepository.findByHandle(handle);

        coleccionAModificar.agregarFuente(fuenteAAgregar);

        coleccionRepository.save(coleccionAModificar);
    }
}

