package ar.utb.ba.dsi.estadisticas.controllers;


import ar.utb.ba.dsi.estadisticas.models.DTOs.outputs.EstadisticaOutputDTO;
import ar.utb.ba.dsi.estadisticas.models.entities.TipoArchivo;
import ar.utb.ba.dsi.estadisticas.services.IEstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticasController {
    @Autowired
    private IEstadisticasService estadisticasService;

    @GetMapping("/coleccion/{idColeccion}/provincia")
    public ResponseEntity<EstadisticaOutputDTO> getProvinciasConMasHechosColecion(Long idColeccion) {
        EstadisticaOutputDTO estadisticas = estadisticasService.getProvinciasConMasHechosColeccion(idColeccion);
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/categoria")
    public ResponseEntity<EstadisticaOutputDTO> getCategoriaConMasHechos() {
        EstadisticaOutputDTO estadisticas = estadisticasService.getCategoriaConMasHechos();
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/categoria/{idCategoria}/provincia")
    public ResponseEntity<EstadisticaOutputDTO> getProvinciasConMasHechoCategoria(Long idCategoria) {
        EstadisticaOutputDTO estadisticas = estadisticasService.getProvinciasConMasHechoCategoria(idCategoria);
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/categoria/{idCategoria}/horas")
    public ResponseEntity<EstadisticaOutputDTO> getHorasConMasHechosCategoria(Long idCategoria) {
        EstadisticaOutputDTO estadisticas = estadisticasService.getHorasConMasHechosCategoria(idCategoria);
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/spam")
    public ResponseEntity<EstadisticaOutputDTO> getCantidadSpam() {
        EstadisticaOutputDTO estadisticas = estadisticasService.getCantidadSpam();
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/exportar")
    public ResponseEntity<File> getArchivoEstadisticas(TipoArchivo tipo) {
        File archivo = estadisticasService.getArchivoEstadisticas(tipo);
        return ResponseEntity.ok(archivo);
    }
}

