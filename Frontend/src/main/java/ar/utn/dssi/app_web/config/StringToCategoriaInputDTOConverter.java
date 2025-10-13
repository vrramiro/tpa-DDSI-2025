package ar.utn.dssi.app_web.config;

import ar.utn.dssi.app_web.dto.input.CategoriaInputDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoriaInputDTOConverter implements Converter<String, CategoriaInputDTO> {

    @Override
    public CategoriaInputDTO convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        CategoriaInputDTO categoriaDTO = new CategoriaInputDTO();
        categoriaDTO.setNombreCategoria(source);
        return categoriaDTO;
    }
}
