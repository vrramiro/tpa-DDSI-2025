package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.Filtro;
import ar.utn.dssi.Agregador.models.entities.Ubicacion;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl.*;
import ar.utn.dssi.Agregador.services.IFiltrosService;

import java.util.ArrayList;
import java.util.List;

public class FiltroService implements IFiltrosService {
    public List<CriterioDePertenencia> criteriosDelFiltro;

    @Override
    public Filtro crearFiltro(FiltroInputDTO filtroInputDTO) {
        List<CriterioDePertenencia> criterios = new ArrayList<>();

        if (filtroInputDTO.getCategoria() != null && !filtroInputDTO.getCategoria().isBlank()) {
            Categoria categoria = new Categoria();
                categoria.setNombre(filtroInputDTO.getCategoria());
            criterios.add(new CriterioPorCategoria(categoria));
        }

        if (filtroInputDTO.getFecha_acontecimiento_desde() != null) {
            criterios.add(new CriterioFechaDesde(filtroInputDTO.getFecha_acontecimiento_desde()));
        }

        if (filtroInputDTO.getFecha_acontecimiento_hasta() != null) {
            criterios.add(new CriterioFechaHasta(filtroInputDTO.getFecha_acontecimiento_hasta()));
        }

        if (filtroInputDTO.getLatitud() != null && filtroInputDTO.getLongitud() != null) {
            Ubicacion ubicacion = new Ubicacion(filtroInputDTO.getLatitud(), filtroInputDTO.getLongitud());

            criterios.add(new CriterioUbicacion(ubicacion));
        }

        if (filtroInputDTO.getIdFuente() != null) {
            criterios.add(new CriterioPorFuente(filtroInputDTO.getIdFuente()));
        }

        return Filtro.builder()
                .criteriosDeFiltro(criterios)
                .build();
    }

}

