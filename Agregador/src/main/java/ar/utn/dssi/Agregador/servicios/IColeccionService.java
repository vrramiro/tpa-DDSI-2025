package ar.utn.dssi.Agregador.servicios;

import ar.utn.dssi.Agregador.modelos.DTOs.outputDTO.ColeccionOutputDTO;

import java.util.List;

public interface IColeccionService {
    public List<ColeccionOutputDTO> obtenerColecciones();
    public ColeccionOutputDTO crearColeccion( );//Que recibe?
}
