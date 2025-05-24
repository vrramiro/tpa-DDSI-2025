package ar.utn.dssi.Agregador.servicios.impl;

import ar.utn.dssi.Agregador.modelos.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.modelos.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;
import ar.utn.dssi.Agregador.modelos.repositorio.IHechosRepository;
import ar.utn.dssi.Agregador.servicios.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HechosService implements IHechosService {
    @Autowired
    private IHechosRepository hechosRepository;

    @Override
    public List<HechoOutputDTO> obtenerHechos() {
        try {
            return this.hechosRepository
                    .findall()
                    .stream()
                    .map(this::hechoOutputDTO)
                    .toList();
        } catch (Exception e) {
        }

        return null; //TODO
    }

    @Override
    public List<HechoOutputDTO> importarHechos() {
        return List.of();
    }

    @Override
    public HechoOutputDTO hechoOutputDTO(Hecho hecho) {
        var dtoHecho = new HechoOutputDTO();

        dtoHecho.setTitulo(hecho.getTitulo());
        dtoHecho.setDescripcion(hecho.getDescripcion());
        dtoHecho.setCategoria(hecho.getCategoria());
        dtoHecho.setUbicacion(hecho.getUbicacion());
        dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        dtoHecho.setFechaCarga(hecho.getFechaCarga());

        return dtoHecho;
    }
}
