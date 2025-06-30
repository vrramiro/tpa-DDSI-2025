package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.entities.Filtro;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.ICriterioDeFiltrado;
import ar.utn.dssi.Agregador.services.IFiltrosService;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.TipoCriterio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FiltroService implements IFiltrosService {
    public List<ICriterioDeFiltrado> criteriosDelFiltro;

    @Override
    public Filtro crearFiltro(FiltroInputDTO filtroInputDTO) {
        Filtro filtro = new Filtro();


        return filtro;
    }

    private List<TipoCriterio> mapearCriterio(FiltroInputDTO filtroDTO) {

        List<TipoCriterio> tipoCriterios = new ArrayList<>();

        if (!filtroDTO.getCategoria().isEmpty()) {
            tipoCriterios.add(TipoCriterio.CATEGORIA);
        }

        if (!filtroDTO.getFecha_acontecimiento_desde().toString().isBlank()) {
            tipoCriterios.add(TipoCriterio.FECHA_DESDE);
        }

        if (!filtroDTO.getFecha_acontecimiento_hasta().toString().isBlank()) {
            tipoCriterios.add(TipoCriterio.FECHA_HASTA);
        }

        if (!(filtroDTO.getLatitud().toString().isBlank() && filtroDTO.getLongitud().toString().isBlank())) {
            tipoCriterios.add(TipoCriterio.UBICACION);
        }

        if (filtroDTO.getIdFuente().describeConstable().isPresent()) {
            tipoCriterios.add(TipoCriterio.FUENTE);
        }

        return tipoCriterios;
    }
}

/*
public class FiltroInputDTO {
    private String categoria;
    private LocalDate fecha_reporte_desde;
    private LocalDate fecha_reporte_hasta;
    private LocalDate fecha_acontecimiento_desde;
    private LocalDate fecha_acontecimiento_hasta;
    private Double longitud;
    private Double latitud;
}