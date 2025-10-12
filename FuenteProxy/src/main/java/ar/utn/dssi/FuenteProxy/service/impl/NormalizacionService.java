package ar.utn.dssi.FuenteProxy.service.impl;

import ar.utn.dssi.FuenteProxy.error.HechoNormalizadoNoObtenido;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.normalizador.INormalizadorAdapter;
import ar.utn.dssi.FuenteProxy.service.INormalizacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NormalizacionService implements INormalizacionService {
  private final INormalizadorAdapter normalizadorAdapter;

  public NormalizacionService(INormalizadorAdapter normalizadorAdapter) {
    this.normalizadorAdapter = normalizadorAdapter;
  }

  @Override
  public Hecho normalizar(Hecho original) {
    try {
      Hecho normalizado = normalizadorAdapter
          .obtenerHechoNormalizado(original)
          .block();

      if (normalizado == null) {
        log.info("El normalizador no pudo normalizar el hecho con clave: {}", original.combinacionIdExternoFuenteId());
        return null;
      }

      if (normalizado.getUbicacion() == null) {
        throw new HechoNormalizadoNoObtenido("El normalizador no pudo obtener la ubicacion del hecho con clave: " + original.combinacionIdExternoFuenteId());
      }

      if (normalizado.getCategoria().getNombre() == null) {
        log.info("No se normalizo categoria del hecho: {}", original.combinacionIdExternoFuenteId());
        log.info("Titulo del hecho sin categoria: {}", original.getTitulo());
        throw new HechoNormalizadoNoObtenido("El normalizador no pudo obtener la categoria del hecho con clave: " + original.combinacionIdExternoFuenteId());
      }

      normalizado.setEliminado(false);
      normalizado.setFuente(original.getFuente());
      normalizado.setIdExterno(original.getIdExterno());

      return normalizado;
    } catch (Exception e) {
      throw new HechoNormalizadoNoObtenido("Error al normalizar el hecho con clave: " + original.combinacionIdExternoFuenteId() + ", " + e.getMessage());
    }
  }
}

