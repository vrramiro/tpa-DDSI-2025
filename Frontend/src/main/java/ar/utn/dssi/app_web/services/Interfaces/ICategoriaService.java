package ar.utn.dssi.app_web.services.Interfaces;

import ar.utn.dssi.app_web.dto.CategoriaDTO;

import java.util.List;

public interface ICategoriaService {
    List<CategoriaDTO> obtenerCategorias();
}
