package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import java.util.List;

public interface IFuentesService {
  List<Hecho> hechosNuevos();
  List<Hecho> hechosMetamapa();
  Fuente obtenerFuentePorId(Long idFuente);
  List<Fuente> obtenerFuentesPorTiposDeFuente(List<String> fuentesDTO);
}
