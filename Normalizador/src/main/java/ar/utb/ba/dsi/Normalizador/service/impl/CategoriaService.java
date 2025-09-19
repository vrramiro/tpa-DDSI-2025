package ar.utb.ba.dsi.Normalizador.service.impl;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.CategoriaInputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;
import ar.utb.ba.dsi.Normalizador.models.entities.errores.CategoriaNotFoundException;
import ar.utb.ba.dsi.Normalizador.models.mappers.MapperDeCategorias;
import ar.utb.ba.dsi.Normalizador.models.repository.ICategoriaRepository;
import ar.utb.ba.dsi.Normalizador.service.ICategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService implements ICategoriaService {
    private final ICategoriaRepository categoriaRepository;

    public CategoriaService(ICategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public CategoriaOutputDTO normalizarCategoriaOutPut(CategoriaInputDTO categoria) {
        return MapperDeCategorias.categoriaToOutputDTO(this.normalizarCategoria(MapperDeCategorias.categoriaFromInputDTO(categoria)));
    }

    @Override
    public Categoria normalizarCategoria(Categoria categoriaInput) {
        String categoriaExterna = categoriaInput.getNombre().toLowerCase();

        Categoria categoriaNormalizada = categoriaRepository.findCategoriaByCategoriaExterna(categoriaExterna);

        if (categoriaNormalizada == null) {
            throw new CategoriaNotFoundException("Categoría no encontrada: " + categoriaInput.getNombre());
        }

        return categoriaNormalizada;
    }


    @Override
    public List<CategoriaOutputDTO> obtenerCategorias() {
        return categoriaRepository.findAll().stream().map(MapperDeCategorias::categoriaToOutputDTO).collect(Collectors.toList());
    }
}
