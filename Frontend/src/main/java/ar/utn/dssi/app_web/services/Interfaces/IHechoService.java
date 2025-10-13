package ar.utn.dssi.app_web.services.Interfaces;

import ar.utn.dssi.app_web.dto.input.HechoInputDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;

public interface IHechoService {
    PageResponseDTO<HechoOutputDTO> listarHechosDeColeccion(long coleccionId, int page, int size, String filtro, String sort);
    Boolean crearHecho(HechoInputDTO hecho);
}
