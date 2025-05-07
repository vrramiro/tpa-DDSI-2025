package ar.utn.ba.dsi.MetaMap.controladores;

import ar.utn.ba.dsi.MetaMap.modelos.DTOs.output.HechoOutputDTO;
import ar.utn.ba.dsi.MetaMap.servicios.IHechosServicios;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
