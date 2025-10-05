package ar.utn.dssi.app_web.repositoriePrueba;

import ar.utn.dssi.app_web.DTO.HechoDTO;

import java.util.ArrayList;
import java.util.List;

public class HechoRepository {
    List<HechoDTO> hechos = new ArrayList<>();

    public HechoRepository() {
        inicializar();
    }

    private void inicializar() {
        HechoDTO hecho1 = new HechoDTO();
        hecho1.setTitulo("Titulo 1");
        hecho1.setDescripcion("Descripcion 1");
        hecho1.setCategoria("Categoria 1");
        hecho1.setUbicacion("Ubicacion 1");
        hecho1.setFechaAcontecimiento("Fecha 1");
        hecho1.setFechaCarga("Fecha 1");
        hecho1.setLongitud("Longitud 1");
        hecho1.setLatitud("Latitud 1");
        hecho1.setContenidoMultimedia(new ArrayList<>());

        HechoDTO hecho2 = new HechoDTO();
        hecho2.setTitulo("Titulo 2");
        hecho2.setDescripcion("Descripcion 2");
        hecho2.setCategoria("Categoria 2");
        hecho2.setUbicacion("Ubicacion 2");
        hecho2.setFechaAcontecimiento("Fecha 2");
        hecho2.setFechaCarga("Fecha 2");
        hecho2.setLongitud("Longitud 2");
        hecho2.setLatitud("Latitud 2");
        hecho2.setContenidoMultimedia(new ArrayList<>());

        HechoDTO hecho3 = new HechoDTO();
        hecho3.setTitulo("Titulo 3");
        hecho3.setDescripcion("Descripcion 3");
        hecho3.setCategoria("Categoria 3");
        hecho3.setUbicacion("Ubicacion 3");
        hecho3.setFechaAcontecimiento("Fecha 3");
        hecho3.setFechaCarga("Fecha 3");
        hecho3.setLongitud("Longitud 3");
        hecho3.setLatitud("Latitud 3");
        hecho3.setContenidoMultimedia(new ArrayList<>());

        hechos.add(hecho1);
        hechos.add(hecho2);
        hechos.add(hecho3);
    }
}
