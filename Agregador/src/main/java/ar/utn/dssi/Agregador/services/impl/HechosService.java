package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.entities.Filtro;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.Ubicacion;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import ar.utn.dssi.Agregador.services.IColeccionService;
import ar.utn.dssi.Agregador.services.IFuentesService;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;


@Service
public class HechosService implements IHechosService {
    @Autowired
    private IHechosRepository hechosRepository;

    @Autowired
    private IColeccionService coleccionService;

    @Autowired
    private IFuentesService fuentesService;

    //OPERACIONES CRUD SOBRE LOS HECHOS
    @Override
    public Hecho crearHecho(HechoInputDTO hechoInputDTO, Long IdFuente) {   //CREATE
        try {
            var hecho = new Hecho();
            var ubicacion = new Ubicacion(hechoInputDTO.getUbicacion().getLatitud(), hechoInputDTO.getUbicacion().getLongitud());
            var categoria = new Categoria();

            hecho.setTitulo(hechoInputDTO.getTitulo());
            hecho.setDescripcion(hechoInputDTO.getDescripcion());
            hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());
            hecho.setFechaCarga(LocalDateTime.now());
            hecho.setUbicacion(ubicacion);
            hecho.setCategoria(categoria);
            hecho.setOrigen(hechoInputDTO.getOrigen());
            hecho.setVisible(true);
            hecho.setContenidoMultimedia(hechoInputDTO.getContenidoMultimedia());
            hecho.setIdHecho(hechosRepository.obtenerUltimoId());
            hecho.setIdOrigen(hechoInputDTO.getIdEnFuente());
            hecho.setIdFuente(IdFuente);

            return hecho;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el hecho: " + e.getMessage(), e);
        }
    }

    @Override
    public HechoOutputDTO obtenerHechoPorId(Long idHecho) {     //READ
        try {
            Hecho hecho = hechosRepository.findById(idHecho);

            return hechoOutputDTO(hecho);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el hecho por id: " + e.getMessage(), e);
        }
    }

    @Override
    public List<HechoOutputDTO> obtenerHechos(FiltroInputDTO filtroInputDTO, IModoNavegacion modoNavegacion) {       //READ
        try {
            List<Hecho> hechosModulo = this.hechosRepository.findall();

            Filtro filtro = this.crearFiltro(filtroInputDTO);

            var hechos = modoNavegacion.obtenerHechos(filtro, hechosModulo)
                    .stream()
                    .filter(filtro::loCumple)
                    .map(this::hechoOutputDTO)
                    .toList();

            if (hechos.isEmpty()) {
                throw new RuntimeException("No hay hechos disponibles");
            }

            return hechos;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los hechos: " + e.getMessage(), e);
        }
    }


    private Filtro crearFiltro(FiltroInputDTO filtros) {
        Filtro filtro = new Filtro();

        //TODO: ASIGNAR ATRIBUTOS DEL INPUT

        return filtro;
    }

    @Override
    public Mono<Void> actualizarHechos() {      //UPDATE
        try {
            return Flux
                .fromIterable(this.importarNuevosHechos())
                .flatMap(hecho -> {
                    coleccionService.refrescarColecciones(hecho);
                    return Mono.empty();
                })
                .then();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar los hechos: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarHecho(Long IdHecho) {       //DELETE
        try {
            //TODO revisar gestion de eliminacion en fuente si es estatica o dinamica => ver que fuente es y mandarle que lo elimine
            Hecho hecho = this.hechosRepository.findById(IdHecho);
            hecho.setVisible(false);
            hechosRepository.update(hecho);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el hecho: " + e.getMessage(), e);
        }
    }


    //IMPORTAR HECHOS NUEVOS DESDE LA FUENTE
    private List<Hecho> importarNuevosHechos() {
        try {
            List<Hecho> hechosNuevos = fuentesService.obtenerNuevosHechos();

            return hechosNuevos;
        } catch (Exception e) {
            throw new RuntimeException("Error al importar los hechos: " + e.getMessage(), e);
        }
    }


    //GUARDADO DE HECHOS
    public void guardarHecho(Hecho hecho){
        try {
            Long idEnFuente = hecho.getIdOrigen();
            Long idFuente = hecho.getIdFuente();

            Hecho hechoObtenido = hechosRepository.findByIdOrigenAndIdFuente(idEnFuente, idFuente);

            if(hechoObtenido == null) {
                hechosRepository.save(hecho);
            } else {
                hechosRepository.update(hecho);
            }
        } catch(Exception e) {
            throw new RuntimeException("Error al guardar el hecho: " + e.getMessage(), e);
        }
    }


    //MODIFICACION DEL TIPO DE HECHO PARA SU TRANSMISION ENTRE MODULOS
    @Override
    public HechoOutputDTO hechoOutputDTO(Hecho hecho) { //Lo vamos a usar cuando queremos mostrar los hechos de la coleccion
        try{
            var dtoHecho = new HechoOutputDTO();

            dtoHecho.setTitulo(hecho.getTitulo());
            dtoHecho.setDescripcion(hecho.getDescripcion());
            dtoHecho.setCategoria(hecho.getCategoria());
            dtoHecho.setUbicacion(hecho.getUbicacion());
            dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
            dtoHecho.setFechaCarga(hecho.getFechaCarga());
            dtoHecho.setContenidoMultimedia(hecho.getContenidoMultimedia());

            return dtoHecho;
        } catch(Exception e) {
            throw new RuntimeException("Error al obtener el dto de hecho: " + e.getMessage(), e);
        }
    }

    public HechoOutputDTO hechoOutputDTOProxy(HechoInputDTO hecho) { //Lo vamos a usar cuando queremos mostrar los hechos de la fuenteProxy
        try {
            var dtoHecho = new HechoOutputDTO();

            dtoHecho.setTitulo(hecho.getTitulo());
            dtoHecho.setDescripcion(hecho.getDescripcion());
            dtoHecho.setCategoria(hecho.getCategoria());
            dtoHecho.setUbicacion(hecho.getUbicacion());
            dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
            dtoHecho.setFechaCarga(hecho.getFechaCarga());
            dtoHecho.setContenidoMultimedia(hecho.getContenidoMultimedia());

            return dtoHecho;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el dto de hecho desde fuente proxy: " + e.getMessage(), e);
        }

    }
}