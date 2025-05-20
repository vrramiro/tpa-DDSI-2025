package ar.utn.dssi.FuenteEstatica.controllers;

import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteEstatica.services.IHechoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/hecho")

public class HechosController {
    @Autowired
    private IHechoServicio hechoServicio;

    @PostMapping("/importar")
    public void importarArchivo(@RequestParam File archivo) {
        this.hechoServicio.importarArchivo(archivo);
    }

    @GetMapping
    public List<HechoOutputDTO> obtenerHecho() {
        return this.hechoServicio.obtenerHechos();
    }
}
