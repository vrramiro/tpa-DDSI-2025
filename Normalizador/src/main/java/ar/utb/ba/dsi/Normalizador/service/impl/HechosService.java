package ar.utb.ba.dsi.Normalizador.service.impl;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.HechoInputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.HechoOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.UbicacionOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;
import ar.utb.ba.dsi.Normalizador.models.entities.Hecho;
import ar.utb.ba.dsi.Normalizador.models.entities.Ubicacion;
import ar.utb.ba.dsi.Normalizador.models.mappers.MapperDeCategorias;
import ar.utb.ba.dsi.Normalizador.models.mappers.MapperDeHechos;
import ar.utb.ba.dsi.Normalizador.models.mappers.MapperDeUbicacion;
import ar.utb.ba.dsi.Normalizador.models.repository.ICategoriaRepository;
import ar.utb.ba.dsi.Normalizador.service.ICategoriaService;
import ar.utb.ba.dsi.Normalizador.service.IHechosService;
import ar.utb.ba.dsi.Normalizador.service.IUbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HechosService implements IHechosService {

    @Autowired
    private IUbicacionService ubicacionService;
    private ICategoriaService categoriaService;

    @Override
    public HechoOutputDTO curarHecho(HechoInputDTO hechoInput) {
        Hecho hecho = MapperDeHechos.hechoFromInput(hechoInput);

        // Normalizo ubicacion
        Ubicacion ubicacionHecho = MapperDeUbicacion.ubicacionFromOutput(
                ubicacionService.obtenerUbicacion(
                        hecho.getUbicacion().getLatitud(),
                        hecho.getUbicacion().getLongitud()
                )
        );
        hecho.setUbicacion(ubicacionHecho);

        //Normalizo Categoria
        Categoria categoriaHecho = MapperDeCategorias.categoriaFromOutputDTO(
                categoriaService.normalizarCategoria(
                        hechoInput.getCategoria()
                )
        );
        hecho.setCategoria(categoriaHecho);

        //Normalizo fecha

        //Sanitizo Hecho


        return MapperDeHechos.hechoOutputDTO
    }
}
