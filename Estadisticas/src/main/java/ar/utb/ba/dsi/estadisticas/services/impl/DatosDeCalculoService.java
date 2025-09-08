package ar.utb.ba.dsi.estadisticas.services.impl;

import ar.utb.ba.dsi.estadisticas.models.adapters.agregador.IAgregadorAdapter;
import ar.utb.ba.dsi.estadisticas.models.adapters.normalizador.INormalizadorAdapter;
import ar.utb.ba.dsi.estadisticas.models.entities.DatosDeCalculo;
import ar.utb.ba.dsi.estadisticas.services.IDatosDeCaluculoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatosDeCalculoService implements IDatosDeCaluculoService {
    IAgregadorAdapter agregadorAdapter;
    INormalizadorAdapter normalizadorAdapter;

    public DatosDeCalculoService(IAgregadorAdapter agregadorAdapter, INormalizadorAdapter normalizadorAdapter) {
        this.agregadorAdapter = agregadorAdapter;
        this.normalizadorAdapter = normalizadorAdapter;
    }

    @Override
    public DatosDeCalculo obtenerDatosDeCalculo() {
        try {
            DatosDeCalculo datosDeCalculo = new DatosDeCalculo();

            datosDeCalculo.setCategorias(normalizadorAdapter.obtenerCategorias());
            datosDeCalculo.setColecciones(agregadorAdapter.obtenerColecciones());
            datosDeCalculo.setHechos(agregadorAdapter.obtenerHechos());
            datosDeCalculo.setSolicitudDeEliminacion(agregadorAdapter.obtenerSolicitudes());

            return datosDeCalculo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
