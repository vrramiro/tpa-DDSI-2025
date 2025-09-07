package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.Ubicacion;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.mappers.MapperDeHechos;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import ar.utn.dssi.Agregador.services.IFuentesService;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class HechosService implements IHechosService {
    @Autowired
    private IHechosRepository hechosRepository;

    @Autowired
    private IColeccionRepository coleccionRepository;

    @Autowired
    private IFuentesService fuentesService;

    @Override
    public Hecho crearHecho(HechoInputDTO hechoInputDTO, Long IdFuente) {
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
            hecho.setVisible(true);
            hecho.setContenidoMultimedia(hechoInputDTO.getContenidoMultimedia());
            hecho.setId(hechosRepository.obtenerUltimoId());
            hecho.setIdEnFuente(hechoInputDTO.getIdEnFuente());
            hecho.setFuente(new Fuente()/*ESTO NO ESTA BIEN!!! ES PARA QUE NO TIRE ERROR AHORA*/); //TODO: OBTENER LA FUENTE EN BASE A SU ID O ALGUNA FORMA

            return hecho;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el hecho: " + e.getMessage(), e);
        }
    }

    @Override
    public HechoOutputDTO obtenerHechoPorId(Long idHecho) {
        try {
            Hecho hecho = hechosRepository.findById(idHecho);

            return MapperDeHechos.hechoOutputDTO(hecho);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el hecho por id: " + e.getMessage(), e);
        }
    }

    @Override
    public List<HechoOutputDTO> obtenerHechos() {
        try {
            List<HechoOutputDTO> hechos = this.hechosRepository
                    .findall()
                    .stream()
                    .map(MapperDeHechos::hechoOutputDTO)
                    .toList();

            hechos.addAll(this.fuentesService.hechosMetamapa().stream().map(MapperDeHechos::hechoOutputDTO).toList());

            return hechos;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los hechos: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarHecho(Long IdHecho) {
        try {
            //TODO revisar gestion de eliminacion en fuente si es estatica o dinamica => ver que fuente es y mandarle que lo elimine
            Hecho hecho = this.hechosRepository.findById(IdHecho);
            hecho.setVisible(false);
            hechosRepository.update(hecho);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el hecho: " + e.getMessage(), e);
        }
    }

    @Override
    public void importarNuevosHechos() {
        try {
            List<Hecho> hechosNuevos = this.fuentesService.hechosNuevos();

            //TODO: ACA NORMALIZAR LOS HECHOS..

            List<Coleccion> colecciones = coleccionRepository.findAll();

            //TODO implementar metodo agregarHechos en coleccion
            colecciones.parallelStream().forEach(coleccion -> coleccion.agregarHechos(hechosNuevos)); //trabaja varias colecciones por core

            coleccionRepository.saveAll(colecciones);
        } catch (Exception e) {
            throw new RuntimeException("Error al importar los hechos: " + e.getMessage(), e);
        }
    }


    //GUARDADO DE HECHOS
    public void guardarHecho(Hecho hecho){
        try {
            Long idEnFuente = hecho.getIdEnFuente();
            Long idFuente = hecho.getFuente().getId();

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
}