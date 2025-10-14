package ar.utn.dssi.FuenteProxy.service;

import ar.utn.dssi.FuenteProxy.models.entities.Hecho;

public interface INormalizacionService {
  Hecho normalizar(Hecho hecho);
}
