package ar.utn.dssi.app_web.mappers;

import ar.utn.dssi.app_web.dto.CategoriaDTO;
import ar.utn.dssi.app_web.dto.input.HechoInputDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import org.springframework.stereotype.Component;

@Component
public class HechoMapper {

    static public HechoInputDTO outputToInput(HechoOutputDTO output) {
        HechoInputDTO input = new HechoInputDTO();
        input.setTitulo(output.getTitulo());
        input.setDescripcion(output.getDescripcion());
        input.setFechaAcontecimiento(output.getFechaAcontecimiento());

        if (output.getCategoria() != null) {
            CategoriaDTO categoria = new CategoriaDTO();
            categoria.setCategoria(output.getCategoria());
            input.setCategoria(categoria);
        }

        if (output.getUbicacion() != null) {
            input.setLatitud(output.getUbicacion().getLatitud());
            input.setLongitud(output.getUbicacion().getLongitud());
        }

        return input;
    }
}
