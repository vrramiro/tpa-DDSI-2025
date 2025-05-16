package ar.utn.dssi.FuenteEstatica.services;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;

import java.util.List;

public interface IHechoServicio {
    List<Hecho> extraerHechos();
    List<Hecho> obtenerHechos();
}
