package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoProxy;
import ar.utn.dssi.Agregador.models.entities.fuente.impl.FuenteProxy;
import ar.utn.dssi.Agregador.models.repositories.IFuenteRepository;
import ar.utn.dssi.Agregador.services.IFuentesService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FuentesService implements IFuentesService {
  private final IFuenteRepository fuenteRepository;

  public FuentesService(IFuenteRepository fuenteRepository) {
    this.fuenteRepository = fuenteRepository;
  }

  @Override
  public Fuente obtenerFuentePorId(Long id) {
    return this.fuenteRepository.findById(id).orElse(null);
  }

  //obtener hechos de las fuentes
  @Override
  public List<Hecho> hechosNuevos() {
    return this.fuenteRepository.findAll().stream()
        .flatMap(fuente -> actualizarFuenteYObtenerHechos(fuente).stream())
        .toList();
  }

  private List<Hecho> actualizarFuenteYObtenerHechos(Fuente fuente) {
    List<Hecho> hechos = fuente.getTipoFuente().hechosNuevos(fuente);
    fuente.setUltimaActualizacion(LocalDateTime.now());
    this.fuenteRepository.save(fuente);
    return hechos;
  }

  //Obtener hechos de fuentes metamapa
  @Override
  public List<Hecho> hechosMetamapa() {
    return this.fuenteRepository.findByTipoFuente(new FuenteProxy()).stream()
        .flatMap(fuente -> ((ITipoProxy) fuente.getTipoFuente()).hechosMetamapa(fuente).stream())
        .toList();
  }

  @Override
  public List<Fuente> obtenerFuentesPorTiposDeFuente(List<String> tiposDeFuente) {
    List<String> tiposNormalizados = tiposDeFuente.stream()
        .map(tipo -> tipo.trim().toUpperCase())
        .toList();

    return this.fuenteRepository.findFuenteByTipoFuenteIn(tiposNormalizados);
  }
}
