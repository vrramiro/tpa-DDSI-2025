package ar.utn.ba.dsi.MetaMap.prueba.controladores;

import ar.utn.ba.dsi.MetaMap.prueba.modelos.DTOs.output.HechoOutputDTO;
import ar.utn.ba.dsi.MetaMap.prueba.servicios.IHechosServicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HechosControlador {
    @Autowired
    private IHechosServicios hechosServicios;

    @GetMapping
    public List<HechoOutputDTO> buscarHechos() {
        return this.hechosServicios.buscarHechos();
    }
}
