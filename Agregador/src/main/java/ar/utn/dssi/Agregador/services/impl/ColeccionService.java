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

        return colecciones
                .stream()
                .map(this::verificarActualizada)
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
        this.agregarACache(coleccion);  //Se van a actualizar los hechos asincronincamente

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
            Coleccion coleccion = this.verificarActualizada(coleccionRepository.findByHandle(handle));
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

    //CREO LA COLECCION OUTPUT
    private ColeccionOutputDTO coleccionOutputDTO(Coleccion coleccion) {
        var coleccionDto = new ColeccionOutputDTO();

        coleccionDto.setTitulo(coleccion.getTitulo());
        coleccionDto.setDescripcion(coleccion.getDescripcion());
        coleccionDto.setHechos(coleccion.getHechos());

        return coleccionDto;
    }

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

    //AGREGACION DE UN CRITERIO DE PERTENENCIA
    @Override
    public void agregarCriterioDePertenencia(ICriterioDeFiltrado nuevoCriterio, String handle) {
        Coleccion coleccion = coleccionRepository.findByHandle(handle);
        coleccion.getCriteriosDePertenecias().add(nuevoCriterio);
        this.agregarACache(coleccion);
    }

    //ELIMINACION DE UN CRITERIO DE PERTENENCIA
    @Override
    public void eliminarCriterioDePertenencia(ICriterioDeFiltrado nuevoCriterio, String handle) {
        Coleccion coleccion = coleccionRepository.findByHandle(handle);
        coleccion.getCriteriosDePertenecias().remove(nuevoCriterio);
        this.agregarACache(coleccion);
    }

    //ACTUALIZAR ALGORITMO DE CONSENSO EN LA COLECCION
    @Override
    public void actualizarAlgoritmo(String handle, AlgoritmoConsenso algoritmoConsenso) {
        Coleccion coleccion = coleccionRepository.findByHandle(handle);
        coleccion.setAlgoritmoConsenso(algoritmoConsenso);
        coleccionRepository.update(coleccion);

        //TODO: HACE FALTA CACHE?
    }

    //REFRESCO DE LOS HECHOS EN UNA COLECCION
    private Mono<Void> refrescarHechosEnColeccion(Coleccion coleccion) {
        return Flux.fromIterable(hechosRepositorio.findall())
                .flatMap(hecho -> Mono.fromRunnable(() -> this.guardarEnColeccion(coleccion, hecho)))
                .then(Mono.fromRunnable(() -> {
                    coleccion.setActualizada(Boolean.TRUE);
                    this.eliminarDeCache(coleccion.getHandle());
                }));
    }



    private Coleccion verificarActualizada(Coleccion coleccion){
        if (coleccion.getActualizada() == Boolean.TRUE) {
            return coleccion;
        } else if (coleccion.getActualizada() == Boolean.FALSE && coleccionCache.containsKey(coleccion.getHandle())) {
            this.refrescarHechosEnColeccion(coleccion)
                    .then(Mono.fromRunnable(() -> coleccionRepository.update(coleccion)))
                        .subscribe();;
            return coleccion;
        } else {
            throw new IllegalStateException("No se puede actualizar el coleccion, se desconoce su estado actual");
        }
    }

    /*/////////////////////// CRONS EN COLECCIONES ///////////////////////*/

    //PROCESO DE REFRESCO DE COLECCIONES Y HECHOS USADO POR SCHEDULER
    @Override
    public Mono<Void> refrescarColecciones(Hecho hecho){    //TODO: SOLO RESTA SABER CUANDO LIMPIO LA CACHE Y MARCO LOS HECHOS COMO ACTUALIZADOS... Nose si es depues de que se ejecute esta funcion porque se invoca la cantidad de veces necesarias dependiendo los hechos
        return Flux
                .fromIterable(coleccionRepository.findall())
                .flatMap(coleccion -> {
                    this.guardarEnColeccion(coleccion, hecho);
                    return Mono.empty();
                })
                .then();

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


    /*/////////////////////// OPERACIONES CRUD EN CACHE ///////////////////////*/
    private void agregarACache(Coleccion coleccion) {
        String handle = coleccion.getHandle();
        coleccionCache.put(handle, coleccion); //EL PUT SI YA EXISTE PISA
    }

    private Coleccion obtenerDeCache(String handle) {
        return coleccionCache.get(handle);
    }

    private void eliminarDeCache(String handle) {
        coleccionCache.remove(handle);
    }

    private void limpiarCache() {
        // marcar todas las colecciones como actualizadas
        coleccionCache.values().forEach(c -> c.setActualizada(Boolean.TRUE));
        coleccionCache.clear();
    }

}

