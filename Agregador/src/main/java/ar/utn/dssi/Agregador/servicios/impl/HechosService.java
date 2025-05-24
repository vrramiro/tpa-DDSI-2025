package ar.utn.dssi.Agregador.servicios.impl;

import ar.utn.dssi.Agregador.modelos.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.modelos.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Categoria;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Origen;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Ubicacion;
import ar.utn.dssi.Agregador.modelos.repositorio.IHechosRepository;
import ar.utn.dssi.Agregador.servicios.IColeccionService;
import ar.utn.dssi.Agregador.servicios.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HechosService implements IHechosService {
    @Autowired
    private IHechosRepository hechosRepository;

    @Autowired
    private IColeccionService coleccionService;


    @Override
    public void importarHechos(List<HechoInputDTO> hechos) {
        //TODO: Aca debo realizar la crowntask, donde importamos los hechos desde la api y los vamos creando. Esto es cada 1hs.
        //Api fuente estatica => lista hechos
        //crearHechos(listashechos, FUENTEESTATICA
        //
    }

    @Override
    public HechoOutputDTO hechoOutputDTO(Hecho hecho) { //Lo vamos a usar cuando queremos mostrar los hechos de la coleccion
        var dtoHecho = new HechoOutputDTO();

        dtoHecho.setTitulo(hecho.getTitulo());
        dtoHecho.setDescripcion(hecho.getDescripcion());
        dtoHecho.setCategoria(hecho.getCategoria());
        dtoHecho.setUbicacion(hecho.getUbicacion());
        dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        dtoHecho.setFechaCarga(hecho.getFechaCarga());

        return dtoHecho;
    }

    @Override
    public void crearHecho(HechoInputDTO hechoInputDTO, Origen origen) {
            var hecho = new Hecho();
            var ubicacion = new Ubicacion(hechoInputDTO.getUbicacion().getLatitud(), hechoInputDTO.getUbicacion().getLongitud());
            var categoria = new Categoria();

            hecho.setTitulo(hechoInputDTO.getTitulo());
            hecho.setDescripcion(hechoInputDTO.getDescripcion());
            hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());
            hecho.setFechaCarga(LocalDateTime.now());
            hecho.setUbicacion(ubicacion);
            hecho.setCategoria(categoria);
            hecho.setOrigen(origen);
            hecho.setVisible(true);

            hechosRepository.save(hecho);
            coleccionService.guardarEnColeccion(hecho);
    }
}
