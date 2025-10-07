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
import ar.utn.dssi.Agregador.models.entities.Filtro;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.ModoNavegacion;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.ModoNavegacionFactory;
import ar.utn.dssi.Agregador.models.mappers.MapperDeHechos;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import ar.utn.dssi.Agregador.services.IColeccionService;
import ar.utn.dssi.Agregador.services.ICriterioDePertenenciaService;
import ar.utn.dssi.Agregador.services.IFiltrosService;
import ar.utn.dssi.Agregador.services.IFuentesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColeccionService implements IColeccionService {
    private final IColeccionRepository coleccionRepository;
    private final IHechosRepository hechosRepositorio;
    //private final IHechosService hechosService;
    private final IFuentesService fuentesService;
    private final IFiltrosService filtrosService;
    private final ICriterioDePertenenciaService criterioDePertenenciaService;
    private final ModoNavegacionFactory modoNavegacionFactory;
    private final ApplicationEventPublisher publicador;

    @Override
    @Transactional
    public ColeccionOutputDTO crearColeccion(ColeccionInputDTO input) {
        Coleccion coleccion = new Coleccion();

        this.validarDatosColeccion(input);

        coleccion.setTitulo(input.getTitulo());
        coleccion.setDescripcion(input.getDescripcion());
        coleccion.setActualizada(Boolean.FALSE); //se tiene que cargar con hechos
        List<CriterioDePertenencia> criterios = input.getCriteriosDePertenecias()
                .stream()
                .map(MapperDeCriterio::criterioFromCriterioInputDTO)
                .toList();
        coleccion.setCriterios(criterios);
        coleccion.setConsenso(MapperDeConsenso.consensoFromConsensoInputDTO(input.getConsenso()));

        coleccion = coleccionRepository.save(coleccion);

        publicador.publishEvent(coleccion.getHandle());

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
    public  ColeccionOutputDTO editarColeccion(String handle, ColeccionInputDTO input) {
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

        if(!coleccion.getActualizada()) publicador.publishEvent(coleccion.getHandle());

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
            Filtro filtro = filtrosService.crearFiltro(filtroInputDTO);
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

