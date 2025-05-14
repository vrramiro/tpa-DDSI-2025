package ar.utb.ba.dsi.servicios.impl;

import ar.utn.ba.dsi.MetaMap.modelos.DTOs.output.HechoOutputDTO;
import ar.utn.ba.dsi.MetaMap.modelos.DTOs.input.HechoInputDTO;
import ar.utn.ba.dsi.MetaMap.modelos.entidades.contenido.*;

import ar.utn.ba.dsi.MetaMap.servicios.IHechosServicios;
import ar.utn.ba.dsi.MetaMap.modelos.repositorios.IHechosRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HechosServicios implements IHechosServicios{
    //Inyeccion de dependencias
    @Autowired
    private IHechosRepositorio hechosRepositorio;
    /* List<HechoOutputDTO> buscarHechos();
    HechoOutputDTO buscarHechoPorId(Long id);
    HechoOutputDTO crearHecho(HechoInputDTO hechoInputDTO);
    HechoOutputDTO actualizarHecho(Long id, HechoInputDTO hechoInputDTO);
    void eliminarHecho(Long id);
*/
    //Implementacion de los metodos definidos
    @Override
    public List<HechoOutputDTO> buscarHechos() {
        return this.hechosRepositorio
                .findall()
                .stream()
                .map(this::hechoOutputDTO)
                .toList();
    }
    
    @Override
    public HechoOutputDTO buscarHechoPorId(Long id) {
        var hecho = this.hechosRepositorio.buscarHechoPorId(id);
        if (hecho == null) {
            return null;
        }
        return this.hechoOutputDTO(hecho);
    }
    
    @Override
    public HechoOutputDTO crearHecho(HechoInputDTO hechoInputDTO) {
        var hecho = new Hecho();
        hecho.setTitulo(hechoInputDTO.getTitulo());
        hecho.setDescripcion(hechoInputDTO.getDescripcion());
        //TODO: ver demas setters

        /*var categoria = this.hechosRepositorio.buscarHechoPorId(hechoInputDTO.getIdCategoria());
        hecho.setCategoria(categoria);  //todavia no funciona porque nos falta la categoria desarrollar
        this.hechosRepositorio.save(hecho);*/

        return this.hechoOutputDTO(hecho);
    }

    @Override
    public HechoOutputDTO actualizarHecho(Long id, HechoInputDTO hechoInputDTO) {
        var hecho = this.hechosRepositorio.buscarHechoPorId(id);
        if (hecho == null) {
            return null;
        }
        hecho.setTitulo(hechoInputDTO.getTitulo());
        hecho.setDescripcion(hechoInputDTO.getDescripcion());
        //TODO: ver demas setters
        return this.hechoOutputDTO(hecho);
    }
    
    private HechoOutputDTO hechoOutputDTO(Hecho hecho) {
        HechoOutputDTO hechoOutputDTO = new HechoOutputDTO();
        hechoOutputDTO.setId(hecho.getId());
        hechoOutputDTO.setTitulo(hecho.getTitulo());
        hechoOutputDTO.setDescripcion(hecho.getDescripcion());
        //hechoOutputDTO.setIdCategoria(hecho.getCategoria().getId()); TODAVIA NO FUNCIONA PORQUE NO ESTA LA LOGICA DE ID EN CATEGORIA
        //hechoOutputDTO.setIdUbicacion(hecho.getUbicacion().getId());
        hechoOutputDTO.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        hechoOutputDTO.setFechaCarga(hecho.getFechaCarga());
        // TODO: setEtiquetas ( private List<Long> idsEtiquetas;)

        return hechoOutputDTO;
    }
    
}


