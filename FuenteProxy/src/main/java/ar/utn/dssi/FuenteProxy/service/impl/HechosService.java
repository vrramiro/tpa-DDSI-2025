package ar.utn.dssi.FuenteProxy.service.impl;

import ar.utn.dssi.FuenteProxy.dto.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.Fuente;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.TipoFuente;
import ar.utn.dssi.FuenteProxy.error.FechaUltimaComunicacionFutura;
import ar.utn.dssi.FuenteProxy.error.HechoNoEcontrado;
import ar.utn.dssi.FuenteProxy.mappers.MapperDeHechos;
import ar.utn.dssi.FuenteProxy.models.repositories.IFuenteRepository;
import ar.utn.dssi.FuenteProxy.models.repositories.IHechoRepository;
import ar.utn.dssi.FuenteProxy.service.IHechosService;
import ar.utn.dssi.FuenteProxy.service.INormalizacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HechosService implements IHechosService {
  private final IHechoRepository hechoRepository;
  private final IFuenteRepository fuenteRepository;
  private final INormalizacionService normalizacionService;

  public HechosService(IHechoRepository hechoRepository, IFuenteRepository fuenteService, INormalizacionService normalizacionService) {
    this.hechoRepository = hechoRepository;
    this.fuenteRepository = fuenteService;
    this.normalizacionService = normalizacionService;
  }

  @Override
  public List<HechoOutputDTO> obtenerHechos(LocalDateTime fechaUltimaComunicacion) {
    if(fechaUltimaComunicacion == null) {
      throw new IllegalArgumentException("Error al cargar la fecha de ultima comunicacion");
    }

    if(fechaUltimaComunicacion.isAfter(LocalDateTime.now())) {
      throw new FechaUltimaComunicacionFutura("La fecha de ultima comunicacion no puede ser futura");
    }

    List<Hecho> hechos = hechoRepository.findByEliminadoFalseAndFechaCargaIsAfter(fechaUltimaComunicacion);

    return hechos.stream().map(MapperDeHechos::hechoOutputDTO).collect(Collectors.toList());
  }

  @Override
  public List<HechoOutputDTO> obtenerHechosInstanciasMetamapa() {
    try{
      List<Fuente> fuentes = fuenteRepository.findFuentesByTipoFuente(TipoFuente.METAMAPA);
      List<Hecho> hechosMetamapa = fuentes.parallelStream().flatMap( fuente -> fuente.importarHechos().block().stream()).toList();
      return hechosMetamapa.stream().map(MapperDeHechos::hechoOutputDTO).toList();
    } catch (Exception e){
      throw new RuntimeException("Error al obtener hechos de instancias Metamapa: " + e.getMessage());
    }
  }

  @Override
  public void importarHechos() {
    List<Hecho> hechosNuevos = this.hechosNuevos();

    if(!hechosNuevos.isEmpty()) {
      List<Hecho> hechosAGuardar = hechosNuevos
          .parallelStream()
          .map(hecho -> {
            log.info("Normalizando hecho: {}", hecho.getTitulo());
            return normalizacionService.normalizar(hecho);
          })
          .filter(hecho -> !hecho.getUbicacion().invalida()) // Evito guardar hechos que hayan llegado sin ubicacion porque georef no la reconocio
          .toList(); //TODO abstraer en el service pasando la lista de hechos

      hechosAGuardar.forEach(hecho -> hecho.setFechaCarga(LocalDateTime.now()));

      try {
        System.out.println("termine de normalizar, guardando hechos...");
        hechoRepository.saveAll(hechosAGuardar);
      } catch (Exception e) {
        System.out.println("Error al guardar los hechos: " + e.getMessage());
      }

      System.out.println("termine de normalizar");
    }
  }

  private List<Hecho> hechosNuevos() {
    List<Fuente> fuentes = fuenteRepository.findFuentesByTipoFuenteNot(TipoFuente.METAMAPA);

    List<Hecho> hechosObtenidos = fuentes.stream().flatMap(fuente -> fuente.importarHechos().block().stream()).toList();

    Set<Integer> idsExternos = hechosObtenidos.stream().map(Hecho::getIdExterno).collect(Collectors.toSet());

    Set<Long> idsFuentes = fuentes.stream().map(Fuente::getId).collect(Collectors.toSet());

    Set<String> clavesExistentes = hechoRepository.findByIdExternoInAndFuenteIdIn(idsExternos, idsFuentes)
        .stream()
        .map(Hecho::combinacionIdExternoFuenteId)
        .collect(Collectors.toSet());

    return hechosObtenidos.stream()
        .filter(hecho -> !clavesExistentes.contains(hecho.combinacionIdExternoFuenteId()))
        .toList();
  }

  @Override
  public void eliminarHecho(Long idHecho) {
    Hecho hecho = hechoRepository.findById(idHecho).orElseThrow(() -> new HechoNoEcontrado(idHecho));

    hecho.setEliminado(true);

    hechoRepository.save(hecho);
  }
}


