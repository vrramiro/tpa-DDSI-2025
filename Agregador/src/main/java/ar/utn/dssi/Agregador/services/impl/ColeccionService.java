package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Filtro;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.AlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.ICriterioDeFiltrado;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.impl.CriterioPorFuente;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.ModoNavegacion;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.ModoNavegacionFactory;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import ar.utn.dssi.Agregador.services.IColeccionService;
import ar.utn.dssi.Agregador.services.IFiltrosService;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

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

    @Autowired
    private IFiltrosService filtrosService;

    @Autowired
    private ModoNavegacionFactory modoNavegacionFactory;

    //CACHE PARA LA ACTUALIZACION DE COLECCIONES
    private final Map<String, Coleccion> coleccionCache = new ConcurrentHashMap<>();  //ConcurrentHashMap permite que varios hilos modifiquen la colección al mismo tiempo, y busco por handle

    /*/////////////////////// OPERACIONES CRUD EN COLECCIONES ///////////////////////*/

    //CREATE
    @Override
    public ColeccionOutputDTO crearColeccion(ColeccionInputDTO coleccionInputDTO) {
        var coleccion = new Coleccion();

        coleccion.setTitulo(coleccionInputDTO.getTitulo());
        coleccion.setDescripcion(coleccionInputDTO.getDescripcion());
        coleccion.setHandle(coleccionInputDTO.getHandle());
        coleccion.setCriteriosDePertenecias(coleccionInputDTO.getCriteriosDePertenecias());
        coleccion.setActualizada(Boolean.TRUE);
        coleccionRepository.save(coleccion);
        return this.coleccionOutputDTO(coleccion);
    }

    //OBTENER TODAS LAS COLECCIONES
    @Override
    public List<ColeccionOutputDTO> obtenerColecciones() {
        List<Coleccion> colecciones = coleccionRepository.findall();
            //TODO: SOLO OBTENER VERIFICADAS, SI ALGUNA NO LO ESTA LO ACTUALIZO.
        return colecciones
                .stream()
                .map(this::coleccionOutputDTO)
                .toList();
    }

    //UPDATE
    @Override
    public  ColeccionOutputDTO actualizarColeccion(String handle, ColeccionInputDTO coleccionInputDTO) {
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

    //DELETE
    @Override
    public void eliminarColeccion(String handle) {
        try {
            Coleccion coleccion = coleccionRepository.findByHandle(handle);
            if (coleccion != null) {
                coleccionRepository.delete(coleccion);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la coleccion: " + e.getMessage(), e);
        }
    }

    //NAVEGACION EN LAS COLECCIONES - READ
    @Override
    public List<HechoOutputDTO> navegacionColeccion(FiltroInputDTO filtroInputDTO, ModoNavegacion modoNavegacion, String handle) {
        try {
            Filtro filtro = filtrosService.crearFiltro(filtroInputDTO);
            Coleccion coleccion = coleccionRepository.findByHandle(handle);             //TODO: LUEGO DE OBTENER, VERIFICAR SI ESTA ACTUALIZADA
            IModoNavegacion modo = modoNavegacionFactory.crearDesdeEnum(modoNavegacion);


            var hechosColeccion = coleccion.getHechos()
                    .stream()
                    .filter(hecho -> filtro.loCumple(hecho) && modo.hechoNavegable(hecho, coleccion))
                    .map(hecho -> hechosService.hechoOutputDTO(hecho))
                    .toList();

            if (hechosColeccion.isEmpty()) {
                throw new RuntimeException("No hay hechos disponibles");
            }

            return hechosColeccion;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los hechos: " + e.getMessage(), e);
        }
    }

    /*/////////////////////// OPERACIONES SOBRE COLECCIONES ///////////////////////*/

    //OBTENER HECHOS DE COLECCION
    @Override
    public List<HechoOutputDTO> hechosDeColeccion(String handle) {
        var coleccion = coleccionRepository.findByHandle(handle); //TODO: LUEGO DE OBTENER, VERIFICAR SI ESTA ACTUALIZADA
        var hechosColeccion = coleccion.getHechos();

        return hechosColeccion.stream().map(hechosService::hechoOutputDTO).toList();
    }

    // GUARDAR UN HECHO EN LA COLECCION
    private void guardarEnColeccion(Coleccion coleccion, Hecho hecho) {
        boolean cumpleCriterios = coleccion
                .getCriteriosDePertenecias()
                .stream()
                .allMatch(criterio -> criterio.loCumple(hecho));

        if (cumpleCriterios) {
            coleccion.getHechos().add(hecho);
            coleccionRepository.update(coleccion);
        }
    }

    //AGREGADO DE FUENTE A LA COLECCION
    @Override
    public void agregarFuente(Long idFuente, String handle){
        Fuente fuenteAAgregar = fuentesService.obtenerFuentePorId(idFuente);
        Coleccion coleccionAModificar = coleccionRepository.findByHandle(handle);

        var criterio = new CriterioPorFuente(fuenteAAgregar.getIdFuente());

        this.agregarCriterioDePertenencia(criterio,coleccionAModificar.getHandle());

        coleccionRepository.save(coleccionAModificar);
    }

    //ELIMINADO DE FUENTE A LA COLECCION
    @Override
    public void eliminarFuente(Long idFuente, String handle) {
        Fuente fuenteAAgregar = fuentesService.obtenerFuentePorId(idFuente);
        Coleccion coleccionAModificar = coleccionRepository.findByHandle(handle);

        var criterio = new CriterioPorFuente(fuenteAAgregar.getIdFuente());

        this.eliminarCriterioDePertenencia(criterio,coleccionAModificar.getHandle());

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


    //AGREGACION DE UN CRITERIO DE PERTENENCIA
    @Override
    public void agregarCriterioDePertenencia(ICriterioDeFiltrado nuevoCriterio, String handle) {
        Coleccion coleccion = coleccionRepository.findByHandle(handle);
        coleccion.getCriteriosDePertenecias().add(nuevoCriterio);

        //TODO: REGISTRAR CAMBIOS EN COLECCION
        refrescarHechosEnColeccion(coleccion)
                .then(Mono.fromRunnable(() -> coleccionRepository.update(coleccion)))
                .subscribe();
    }

    //ELIMINACION DE UN CRITERIO DE PERTENENCIA
    @Override
    public void eliminarCriterioDePertenencia(ICriterioDeFiltrado nuevoCriterio, String handle) {
        Coleccion coleccion = coleccionRepository.findByHandle(handle);
        coleccion.getCriteriosDePertenecias().remove(nuevoCriterio);

        //TODO: REGISTRAR CAMBIOS EN COLECCION
        refrescarHechosEnColeccion(coleccion)
                .then(Mono.fromRunnable(() -> coleccionRepository.update(coleccion)))
                .subscribe();
    }

    //REFRESCO DE LOS HECHOS EN UNA COLECCION, DADO POR CAMBIO EN SUS CRITERIOS
    private Mono<Void> refrescarHechosEnColeccion(Coleccion coleccion){ //TODO: ACA TENDRIA QUE VER SI EL REFRESCO DE LOS HECHOS EN UNA COLECCION LO HAGO CON UN SCHEDULER PARA QUE NO SEA SINCRONICO?
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

    //CONSENSO REALIZADO POR CRONTASK
    @Override
    public void realizarConsenso(){
        List<Coleccion> colecciones = coleccionRepository.findall();

        colecciones.forEach(coleccion -> {
                List<Hecho> hechosColeccion = coleccion.getHechos();
                List<Hecho> hechosColeccionProxy = hechosService.obtenerHechosProxy().stream().filter(coleccion::cumpleCriterios).toList();
                List<Hecho> hechosAConsensuar = Stream.concat(hechosColeccion.stream(), hechosColeccionProxy.stream()).toList();
                List<Fuente> fuentes = fuentesService.obtenerFuentes();
                coleccion.aplicarAlgoritmoConsenso(hechosAConsensuar,fuentes);
        });
    }

    //ACTUALIZAR ALGORITMO DE CONSENSO EN LA COLECCION
    @Override
    public void actualizarAlgoritmo(String handle, AlgoritmoConsenso algoritmoConsenso) {
        Coleccion coleccion = coleccionRepository.findByHandle(handle);
        coleccion.setAlgoritmoConsenso(algoritmoConsenso);
        coleccionRepository.update(coleccion);
    }

    /*/////////////////////// OPERACIONES CRUD EN CACHE ///////////////////////*/
    private void agregarACache(Coleccion coleccion) {
        coleccionCache.put(coleccion.getHandle(), coleccion);
    }

    private Coleccion obtenerDeCache(String handle) {
        return coleccionCache.get(handle);
    }

    private void actualizarEnCache(Coleccion coleccion) {
        coleccionCache.put(coleccion.getHandle(), coleccion);
    }

    private void eliminarDeCache(String handle) {
        coleccionCache.remove(handle);
    }

}

