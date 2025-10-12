package ar.utb.ba.dsi.Normalizador.service.impl;

import ar.utb.ba.dsi.Normalizador.dto.Input.HechoInputDTO;
import ar.utb.ba.dsi.Normalizador.dto.output.HechoOutputDTO;
import ar.utb.ba.dsi.Normalizador.mappers.MapperDeFecha;
import ar.utb.ba.dsi.Normalizador.mappers.MapperDeHechos;
import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;
import ar.utb.ba.dsi.Normalizador.models.entities.Hecho;
import ar.utb.ba.dsi.Normalizador.models.entities.Ubicacion;
import ar.utb.ba.dsi.Normalizador.models.entities.sanitizador.Sanitizador;
import ar.utb.ba.dsi.Normalizador.service.ICategoriaService;
import ar.utb.ba.dsi.Normalizador.service.IHechosService;
import ar.utb.ba.dsi.Normalizador.service.IUbicacionService;
import org.springframework.stereotype.Service;

@Service
public class HechosService implements IHechosService {
  private final IUbicacionService ubicacionService;
  private final ICategoriaService categoriaService;

  public HechosService(IUbicacionService ubicacionService, ICategoriaService categoriaService) {
    this.ubicacionService = ubicacionService;
    this.categoriaService = categoriaService;
  }

  @Override
  public HechoOutputDTO normalizarHecho(HechoInputDTO hechoInput) {
    Hecho hecho = new Hecho();

    // Normalizo ubicacion
    Ubicacion ubicacionHecho = ubicacionService.obtenerUbicacion(hechoInput.getLatitud(), hechoInput.getLongitud());
    hecho.setUbicacion(ubicacionHecho);

    //Normalizo Categoria
    String categoriaInput = hechoInput.getCategoria();
    Categoria categoriaHecho = categoriaService.normalizarCategoria(categoriaInput);

    hecho.setCategoria(categoriaHecho);

    //Normalizo fechas
    hecho.setFechaAcontecimiento(MapperDeFecha.fromString(hechoInput.getFechaAcontecimiento()));

    // Sanitizo titulo y descripcion
    hecho.setTitulo(hechoInput.getTitulo());
    hecho.setDescripcion(hechoInput.getDescripcion());
    Sanitizador.sanitizar(hecho);

    return MapperDeHechos.hechoToOutput(hecho);
  }
}
