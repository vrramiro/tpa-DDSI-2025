package ar.utn.dssi.FuenteEstatica.services.impl;

import ar.utn.dssi.FuenteEstatica.dto.output.HechoOutputDTO;
import ar.utn.dssi.FuenteEstatica.error.ErrorGeneralRepositorio;
import ar.utn.dssi.FuenteEstatica.error.RepositorioVacio;
import ar.utn.dssi.FuenteEstatica.error.ValidacionException;
import ar.utn.dssi.FuenteEstatica.mappers.MapperDeHechos;
import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.ILectorDeArchivos;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.impl.FactoryLector;
import ar.utn.dssi.FuenteEstatica.models.entities.normalizadorAdapter.INormalizadorAdapter;
import ar.utn.dssi.FuenteEstatica.models.repositories.IHechosRepositorio;
import ar.utn.dssi.FuenteEstatica.services.IHechoServicio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class HechoServicio implements IHechoServicio {

  private final INormalizadorAdapter normalizadorAdapter;
  private final IHechosRepositorio hechoRepositorio;
  private final FactoryLector factoryLector;
  @Value("${cantidadMinimaDeHechos}")
  private Integer cantidadMinimaDeHechos;


  @Override
  public void importarArchivo(File archivo) {
    String autor = obtenerAutorActual();
    ILectorDeArchivos lectorDeArchivos = factoryLector.crearLector(archivo);
    List<Hecho> hechos = lectorDeArchivos.importarHechos(archivo);

    if (hechos.size() <= cantidadMinimaDeHechos) {
      throw new ValidacionException("El archivo no cumple con la cantidad de minima de hechos:" + cantidadMinimaDeHechos + ", el archivo tiene: " + hechos.size());
    }

    AtomicInteger contador = new AtomicInteger(0);
    int total = hechos.size();

    List<Hecho> hechosNormalizados = hechos.parallelStream()
        .map(hecho -> {
          try {
            Hecho normalizado = normalizadorAdapter.obtenerHechoNormalizado(hecho).block();
            System.out.println("Hecho " + normalizado.getTituloSanitizado() + normalizado.getDescripcionSanitizado() + normalizado.getUbicacion().getCiudad());

            if (normalizado == null) {
              throw new IllegalStateException("El normalizador devolvió null para el hecho: " + hecho.getTitulo());
            }

            int actual = contador.incrementAndGet();
            if (!hecho.getUbicacion().invalida()) {
              log.info("Normalizando hecho {}/{}: {}, Ubicacion {} {} {}"
                  , actual, total, hecho.getTitulo(),
                  normalizado.getUbicacion().getCiudad(),
                  normalizado.getUbicacion().getProvincia(),
                  normalizado.getUbicacion().getPais());
            } else {
              log.info("Normalizando hecho {}/{}: {} - Sin ubicación válida {},{}",
                  actual, total, hecho.getTitulo(), hecho.getUbicacion().getLatitud(),
                  hecho.getUbicacion().getLongitud());
            }

            normalizado.setAutor(autor);
            return normalizado;

          } catch (Exception e) {
            System.err.println("Error normalizando hecho: " + e.getMessage());
            return hecho;
          }
        })
        .filter(hecho -> !hecho.getUbicacion().invalida())
        .toList();

    hechosNormalizados.forEach(hecho -> hecho.setFechaCarga(LocalDateTime.now()));

    hechoRepositorio.saveAll(hechosNormalizados);
  }


  @Override
  public List<HechoOutputDTO> obtenerHechos(LocalDateTime fechaDesde) {

    var hechos = this.hechoRepositorio.findHechosByFechaLimite(fechaDesde);

    if (hechos.isEmpty()) {
      throw new RepositorioVacio("El repositorio esta vacio, no tiene datos.");
    }

    try {
      return hechos.stream().map(MapperDeHechos::hechoOutputDTO).toList();
    } catch (Exception e) {
      throw new ErrorGeneralRepositorio("Error al obtener los hechos.");
    }
  }

  private String obtenerAutorActual() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null
            && authentication.isAuthenticated()
            && !authentication.getPrincipal().equals("anonymousUser")) {
      System.out.println("Autenticando usuario: " + authentication.getName());
      return authentication.getName();
    }

    return null;
  }

}

