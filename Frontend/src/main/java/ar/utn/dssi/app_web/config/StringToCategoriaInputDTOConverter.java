package ar.utn.dssi.app_web.config;

import ar.utn.dssi.app_web.dto.CategoriaDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoriaInputDTOConverter implements Converter<String, CategoriaDTO> {

    @Override
    public CategoriaDTO convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setCategoria(source);
        return categoriaDTO;
    }
}
