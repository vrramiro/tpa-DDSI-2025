package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Categoria;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import ar.utn.dssi.Agregador.models.entities.content.Ubicacion;
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
import java.util.stream.Collectors;

@Service
public class HechosService implements IHechosService {
    @Autowired
    private IHechosRepository hechosRepository;

    @Autowired
    private IColeccionService coleccionService;

    @Autowired
    private IFuentesService fuentesService;

    @Override
    public Mono<Void> actualizarHechos() {
        return Flux
            .fromIterable(this.importarNuevosHechos())
            .flatMap(hecho -> {
                hechosRepository.save(hecho);
                coleccionService.refrescarColecciones(hecho);
                return Mono.empty();
            })
            .then();
    }

    private List<Hecho> importarNuevosHechos() {
        List<HechoInputDTO> hechosNuevos = fuentesService.obtenerNuevosHechos();

        return hechosNuevos.stream().map(this::crearHecho).toList();
    }

    private Hecho crearHecho(HechoInputDTO hechoInputDTO) {
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

        return hecho;
    }

    private HechoOutputDTO hechoOutputDTO(Hecho hecho) { //Lo vamos a usar cuando queremos mostrar los hechos de la coleccion
        var dtoHecho = new HechoOutputDTO();

        dtoHecho.setTitulo(hecho.getTitulo());
        dtoHecho.setDescripcion(hecho.getDescripcion());
        dtoHecho.setCategoria(hecho.getCategoria());
        dtoHecho.setUbicacion(hecho.getUbicacion());
        dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        dtoHecho.setFechaCarga(hecho.getFechaCarga());

        return dtoHecho;
    }

    public void eliminarHecho(Hecho hecho){
        hecho.setVisible(false);
        hechosRepository.update(hecho);
    }

    public Hecho obtenerHechoPorId(Long idHecho){
        return hechosRepository.findById(idHecho);
    }
}