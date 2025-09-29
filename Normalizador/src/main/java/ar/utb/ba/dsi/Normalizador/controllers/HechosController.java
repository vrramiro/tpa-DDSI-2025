package ar.utb.ba.dsi.Normalizador.controllers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.HechoInputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.HechoOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.UbicacionOutputDTO;
import ar.utb.ba.dsi.Normalizador.service.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hecho")
public class HechosController {
    @Autowired
    private IHechosService normalizadorService;

    @PostMapping("/normalizar")
    public ResponseEntity<HechoOutputDTO> normalizarHecho(@RequestBody HechoInputDTO hecho) {
        HechoOutputDTO hechoOutput = normalizadorService.normalizarHecho(hecho);

        ResponseEntity<HechoOutputDTO> response = ResponseEntity.ok(hechoOutput);

        System.out.println("Response enviado: status=" + response.getStatusCode() + ", body=" + response.getBody()); //LOG PARA VER LA RESPUESTA, SACARRR

        return response;
    }

}


