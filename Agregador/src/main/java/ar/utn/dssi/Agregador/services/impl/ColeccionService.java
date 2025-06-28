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

    //OPERACIONES CRUD EN COLECCIONES
    @Override
    public ColeccionOutputDTO crearColeccion(ColeccionInputDTO coleccionInputDTO) {     //CREATE
        var coleccion = new Coleccion();

        coleccion.setTitulo(coleccionInputDTO.getTitulo());
        coleccion.setDescripcion(coleccionInputDTO.getDescripcion());
        coleccion.setHandle(coleccionInputDTO.getHandle());
        coleccion.setCriteriosDePertenecias(coleccionInputDTO.getCriteriosDePertenecias());

        coleccionRepository.save(coleccion);
        return this.coleccionOutputDTO(coleccion);
    }

    @Override
    public List<HechoOutputDTO> obtenerHechosDeColeccion(String handle) {
        var coleccion = coleccionRepository.findByHandle(handle);
        var hechosColeccion = coleccion.getHechos();

        return hechosColeccion.stream().map(hechosService::hechoOutputDTO).toList();
    }

    @Override
    public  ColeccionOutputDTO actualizarColeccion(String handle, ColeccionInputDTO coleccionInputDTO) {      //UPDATE
        Coleccion coleccion = coleccionRepository.findByHandle(handle);

        /*if (coleccion == null)
            throw new RecursoNoEncontradoException("Colección con handle '" + handle + "' no encontrada.");*/
        //TODO: MANEJO ERRORES

        coleccion.setTitulo(coleccionInputDTO.getTitulo());
        coleccion.setDescripcion(coleccionInputDTO.getDescripcion());
        coleccion.setCriteriosDePertenecias(coleccionInputDTO.getCriteriosDePertenecias());

        coleccionRepository.update(coleccion);

        this.refrescarHechosEnColeccion(coleccion);

        return this.coleccionOutputDTO(coleccion);
    }

    @Override
    public void eliminarColeccion(String handle) {       //DELETE
        try {
            Coleccion coleccion = coleccionRepository.findByHandle(handle);
            if (coleccion != null) {
                coleccionRepository.delete(coleccion);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la coleccion: " + e.getMessage(), e);
        }
    }

    //OBTENER TODAS LAS COLECCIONES
    @Override
    public List<ColeccionOutputDTO> obtenerColecciones() {
        var colecciones = coleccionRepository.findall();

        return colecciones
                .stream()
                .map(this::coleccionOutputDTO)
                .toList();
    }

    //OPERACIONES SOBRE LAS FUENTES DE UNA COLECCION
    @Override
    public void agregarFuente(Long idFuente, String handle){
        Fuente fuenteAAgregar = fuentesService.obtenerFuentePorId(idFuente);
        Coleccion coleccionAModificar = coleccionRepository.findByHandle(handle);

        var criterio = new CriterioPorFuente(fuenteAAgregar.getIdFuente());

        agregarCriterioDePertenencia(criterio,coleccionAModificar.getHandle());

        coleccionRepository.save(coleccionAModificar);
    }

    @Override
    public void eliminarFuente(Long idFuente, String handle) {
        Fuente fuenteAAgregar = fuentesService.obtenerFuentePorId(idFuente);
        Coleccion coleccionAModificar = coleccionRepository.findByHandle(handle);

        var criterio = new CriterioPorFuente(fuenteAAgregar.getIdFuente());

        eliminarCriterioDePertenencia(criterio,coleccionAModificar.getHandle());

        coleccionRepository.save(coleccionAModificar);
    }


    //PROCESO DE REFRESCO DE COLECCIONES Y HECHOS USADO POR SCHEDULER
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

    //REFRESCO DE HECHOS EN COLECCION SEGUN CRITERIOS DE PERTENENCIA AGREGADOS
    @Override
    public void agregarCriterioDePertenencia(ICriterioDePertenencia nuevoCriterio, String handle) {
        Coleccion coleccion = coleccionRepository.findByHandle(handle);
        coleccion.getCriteriosDePertenecias().add(nuevoCriterio);

        refrescarHechosEnColeccion(coleccion)
                .then(Mono.fromRunnable(() -> coleccionRepository.update(coleccion)))
                .subscribe();
    }

    @Override
    public void eliminarCriterioDePertenencia(ICriterioDePertenencia nuevoCriterio, String handle) {
        Coleccion coleccion = coleccionRepository.findByHandle(handle);
        coleccion.getCriteriosDePertenecias().remove(nuevoCriterio);

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

    //COLECCION OUTPUT
    private ColeccionOutputDTO coleccionOutputDTO(Coleccion coleccion) {
        var coleccionDto = new ColeccionOutputDTO();

        coleccionDto.setTitulo(coleccion.getTitulo());
        coleccionDto.setDescripcion(coleccion.getDescripcion());
        coleccionDto.setHechos(coleccion.getHechos());

        return coleccionDto;
    }


}

