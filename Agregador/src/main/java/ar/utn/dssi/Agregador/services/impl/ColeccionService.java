package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.error.ColeccionAguardandoActualizacion;
import ar.utn.dssi.Agregador.error.ColeccionNoEncontrada;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FuenteInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.error.DatosDeColeccionFaltantes;
import ar.utn.dssi.Agregador.models.mappers.MapperDeColecciones;
import ar.utn.dssi.Agregador.models.mappers.MapperDeConsenso;
import ar.utn.dssi.Agregador.models.mappers.MapperDeCriterio;
import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.FiltroCriterioPertenecia;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.ModoNavegacion;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.ModoNavegacionFactory;
import ar.utn.dssi.Agregador.models.mappers.MapperDeHechos;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import ar.utn.dssi.Agregador.services.IColeccionService;
import ar.utn.dssi.Agregador.services.ICriterioDePertenenciaService;
import ar.utn.dssi.Agregador.services.IFiltrosService;
import ar.utn.dssi.Agregador.services.IFuentesService;
//import ar.utn.dssi.Agregador.services.IHechosService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ColeccionService implements IColeccionService {
    private final IColeccionRepository coleccionRepository;
    private final IHechosRepository hechosRepositorio;
    //private final IHechosService hechosService;
    private final IFuentesService fuentesService;
    private final IFiltrosService filtrosService;
    private final ICriterioDePertenenciaService criterioDePertenenciaService;
    private final ModoNavegacionFactory modoNavegacionFactory;
    private final Map<String, Coleccion> coleccionCache = new ConcurrentHashMap<>();  //ConcurrentHashMap permite que varios hilos modifiquen la colección al mismo tiempo, y busco por handle

    public ColeccionService(IColeccionRepository coleccionRepository, IHechosRepository hechosRepositorio,
                            /*IHechosService hechosService,*/ FuentesService fuentesService, IFiltrosService filtrosService,
                            ModoNavegacionFactory modoNavegacionFactory, ICriterioDePertenenciaService criterioDePertenenciaService) {
        this.coleccionRepository = coleccionRepository;
        this.hechosRepositorio = hechosRepositorio;
        //this.hechosService = hechosService;
        this.fuentesService = fuentesService;
        this.filtrosService = filtrosService;
        this.modoNavegacionFactory = modoNavegacionFactory;
        this.criterioDePertenenciaService = criterioDePertenenciaService;
    }

    @Override
    @Transactional
    public ColeccionOutputDTO crearColeccion(ColeccionInputDTO input) {
        var coleccion = new Coleccion();

        this.validarDatosColeccion(input);

        coleccion.setTitulo(input.getTitulo());
        coleccion.setDescripcion(input.getDescripcion());
        coleccion.setActualizada(Boolean.TRUE);
        List<CriterioDePertenencia> criterios = input.getCriteriosDePertenecias()
                .stream()
                .map(MapperDeCriterio::criterioFromCriterioInputDTO)
                .toList();
        coleccion.setCriterios(criterios);
        coleccion.setConsenso(MapperDeConsenso.consensoFromConsensoInputDTO(input.getConsenso()));

        coleccionRepository.save(coleccion);

        return MapperDeColecciones.coleccionOutputDTOFromColeccion(coleccion);
    }

    @Override
    @Transactional
    public List<ColeccionOutputDTO> obtenerColecciones() {
        List<Coleccion> colecciones = this.obtenerColeccionesActualizadas();

        return colecciones
            .stream()
            .map(MapperDeColecciones::coleccionOutputDTOFromColeccion)
            .toList();
    }

    @Override
    @Transactional
    public  ColeccionOutputDTO actualizarColeccion(String handle, ColeccionInputDTO input) {
        Coleccion coleccion = this.obtenerColeccion(handle);

        if(!coleccion.getActualizada()) throw new ColeccionAguardandoActualizacion("La coleccion ya fue modificada, aguarde a que se actualice.");

        this.validarDatosColeccion(input);

        if(!coleccion.getTitulo().equals(input.getTitulo())) coleccion.setTitulo(input.getTitulo());
        if(!coleccion.getDescripcion().equals(input.getDescripcion())) coleccion.setDescripcion(input.getDescripcion());

        Boolean cambiaronCriterios = criterioDePertenenciaService.actualizarCriterios(coleccion, input.getCriteriosDePertenecias());

        Boolean cambiaronFuentes = false;

        List<String> tiposDeFuentesInput = input.getFuentes().stream().map(FuenteInputDTO::getTipoFuente).toList();

        List<Fuente> fuentesAAgregar = fuentesService.obtenerFuentesPorTiposDeFuente(tiposDeFuentesInput);

        // Evito actualizar si son las mismas fuentes => comparo por set para no preocuparme por el orden
        if(!fuentesAAgregar.stream().allMatch(coleccion::tieneFuente)) {
            coleccion.actualizarFuentes(fuentesAAgregar);
            cambiaronFuentes = true;
        }

        if(cambiaronCriterios || cambiaronFuentes) {
            coleccion.setActualizada(Boolean.FALSE);
            coleccion.liberarHechos();
        }

        coleccionRepository.save(coleccion);

        this.agregarACache(coleccion);  //Se van a actualizar los hechos asincronincamente => TODO usamos un cron o lanzamos un job

        return MapperDeColecciones.coleccionOutputDTOFromColeccion(coleccion);
    }

    @Override
    public void eliminarColeccion(String handle) {
        if(handle == null || handle.isEmpty()) {
            throw new DatosDeColeccionFaltantes("Handle mal cargado");
        }

        Coleccion coleccion = this.obtenerColeccion(handle);

        if (coleccion != null) {
            coleccionRepository.delete(coleccion);
        }
    }

    @Override
    public List<HechoOutputDTO> navegacionColeccion(FiltroInputDTO filtroInputDTO, ModoNavegacion modoNavegacion, String handle) {
        try {
            FiltroCriterioPertenecia filtro = filtrosService.crearFiltro(filtroInputDTO);
            Coleccion coleccion = this.verificarActualizada(coleccionRepository.findColeccionByHandle(handle)
                .orElseThrow(() -> new ColeccionNoEncontrada(handle)));
            IModoNavegacion modo = modoNavegacionFactory.crearDesdeEnum(modoNavegacion);

          return coleccion.getHechos()
                  .stream()
                  .filter(hecho -> filtro.loCumple(hecho) && modo.hechoNavegable(hecho, coleccion))
                  .map(MapperDeHechos::hechoToOutputDTO)
                  .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los hechos: " + e.getMessage(), e);
        }
    }

    //OBTENER HECHOS DE COLECCION
    @Override
    public List<HechoOutputDTO> obtenerHechosDeColeccion(String handle) {
        var coleccion = this.obtenerColeccion(handle); //TODO: LUEGO DE OBTENER, VERIFICAR SI ESTA ACTUALIZADA
        var hechosColeccion = coleccion.getHechos();

        return hechosColeccion.stream().map(MapperDeHechos::hechoToOutputDTO).toList();
    }

    // GUARDAR UN HECHO EN LA COLECCION
    private void guardarEnColeccion(Coleccion coleccion, Hecho hecho) {
        boolean cumpleCriterios = coleccion
                .getCriterios()
                .stream()
                .allMatch(criterio -> criterio.loCumple(hecho));

        if (cumpleCriterios) {
            coleccion.getHechos().add(hecho);
            coleccionRepository.save(coleccion);
        }
    }

    //REFRESCO DE LOS HECHOS EN UNA COLECCION
    private Mono<Void> refrescarHechosEnColeccion(Coleccion coleccion) {
        return Flux.fromIterable(hechosRepositorio.findAll())
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
                    .then(Mono.fromRunnable(() -> coleccionRepository.save(coleccion)))
                        .subscribe();
            return coleccion;
        } else {
            throw new IllegalStateException("No se puede actualizar el coleccion, se desconoce su estado actual");
        }
    }

    /*/////////////////////// CRONS EN COLECCIONES ///////////////////////*/

    //PROCESO DE REFRESCO DE COLECCIONES Y HECHOS USADO POR SCHEDULER
    //TODO: SOLO RESTA SABER CUANDO LIMPIO LA CACHE Y MARCO LOS HECHOS COMO ACTUALIZADOS...
    // Nose si es depues de que se ejecute esta funcion porque se
    // invoca la cantidad de veces necesarias dependiendo los hechos
    @Override
    public Mono<Void> refrescarColecciones(Hecho hecho){
        return Flux
                .fromIterable(coleccionRepository.findAll())
                .flatMap(coleccion -> {
                    this.guardarEnColeccion(coleccion, hecho);
                    return Mono.empty();
                })
                .then();
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

    private void validarDatosColeccion(ColeccionInputDTO input) {
        if(input.getTitulo() == null || input.getTitulo().isEmpty())
            throw new DatosDeColeccionFaltantes("El titulo es obligatorio.");

        if(input.getDescripcion() == null || input.getDescripcion().isEmpty())
            throw new DatosDeColeccionFaltantes("La descripcion es obligatoria.");

        if(input.getCriteriosDePertenecias() == null || input.getCriteriosDePertenecias().isEmpty())
            throw new DatosDeColeccionFaltantes("Debe tener al menos un criterio de pertenencia.");

        if(input.getFuentes() == null || input.getFuentes().isEmpty())
            throw new DatosDeColeccionFaltantes("Debe tener al menos una fuente.");

        if(input.getConsenso() == null || input.getConsenso().isEmpty())
            throw new DatosDeColeccionFaltantes("El algoritmo de consenso es obligatorio.");
    }

    private Coleccion obtenerColeccion(String handle) {
        return coleccionRepository.findColeccionByHandle(handle).orElseThrow(() -> new ColeccionNoEncontrada(handle));
    }

    private List<Coleccion> obtenerColeccionesActualizadas() {
        return coleccionRepository.findColeccionByActualizada(true);
    }
}

