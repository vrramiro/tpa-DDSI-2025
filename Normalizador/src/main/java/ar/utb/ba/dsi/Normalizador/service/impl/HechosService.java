package ar.utb.ba.dsi.Normalizador.service.impl;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.HechoInputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.HechoOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.UbicacionOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;
import ar.utb.ba.dsi.Normalizador.models.entities.Hecho;
import ar.utb.ba.dsi.Normalizador.models.entities.Ubicacion;
import ar.utb.ba.dsi.Normalizador.models.entities.sanitizador.Sanitizador;
import ar.utb.ba.dsi.Normalizador.models.mappers.MapperDeCategorias;
import ar.utb.ba.dsi.Normalizador.models.mappers.MapperDeFecha;
import ar.utb.ba.dsi.Normalizador.models.mappers.MapperDeHechos;
import ar.utb.ba.dsi.Normalizador.models.mappers.MapperDeUbicacion;
import ar.utb.ba.dsi.Normalizador.models.repository.ICategoriaRepository;
import ar.utb.ba.dsi.Normalizador.service.ICategoriaService;
import ar.utb.ba.dsi.Normalizador.service.IHechosService;
import ar.utb.ba.dsi.Normalizador.service.IUbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class HechosService implements IHechosService {

    @Autowired
    private IUbicacionService ubicacionService;
    private ICategoriaService categoriaService;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public HechoOutputDTO normalizarHecho(HechoInputDTO hechoInput) {
        Hecho hecho = new Hecho();

        // Normalizo ubicacion
        Ubicacion ubicacionHecho = ubicacionService.obtenerUbicacion(hecho.getUbicacion().getLatitud(), hecho.getUbicacion().getLongitud());
        hecho.setUbicacion(ubicacionHecho);

        //Normalizo Categoria
        Categoria categoriaHecho = categoriaService.normalizarCategoria(hecho.getCategoria());
        hecho.setCategoria(categoriaHecho);

        //Normalizo fechas
        hecho.setFechaAcontecimiento(MapperDeFecha.fromString(hechoInput.getFechaAcontecimiento()));
        hecho.setFechaCarga(MapperDeFecha.fromString(hechoInput.getFechaCarga()));

        // Sanitizo titulo y descripcion
        hecho.setTitulo(hechoInput.getTitulo());
        hecho.setDescripcion(hechoInput.getDescripcion());
        Sanitizador.sanitizar(hecho);

        return MapperDeHechos.hechoToOutput(hecho);
    }
}
