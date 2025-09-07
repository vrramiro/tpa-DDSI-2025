package ar.utb.ba.dsi.estadisticas.controllers;


import ar.utb.ba.dsi.estadisticas.services.IEstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticasController {
    @Autowired
    private IEstadisticasService estadisticasService;

}
