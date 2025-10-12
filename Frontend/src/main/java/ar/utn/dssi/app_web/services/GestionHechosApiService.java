package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.output.UbicacionOutputDTO;
import ar.utn.dssi.app_web.services.internal.WebApiCallerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GestionHechosApiService {

  private static final Logger log = LoggerFactory.getLogger(GestionHechosApiService.class);
  private final WebClient webClient;
  private final WebApiCallerService webApiCallerService;
  private final String normalizadorSeriviceUrl;

  @Autowired
  public GestionHechosApiService(
      WebApiCallerService webApiCallerService,
      @Value("${normalizador.service.url}") String normalizadorSeriviceUrl) {
    this.webClient = WebClient.builder().build();
    this.webApiCallerService = webApiCallerService;
    this.normalizadorSeriviceUrl = normalizadorSeriviceUrl;
  }

  public UbicacionOutputDTO obtenerUbicacion(Double latitud, Double longitud) {
    return null;  //para poder probar vistas
  }


  public Boolean ubicacionValida(Double latitud, Double longitud) {
    try {
      obtenerUbicacion(latitud, longitud);
      return true;
    } catch (Exception e) {

    }
    return false;  //para poder probar vistas
  }
}
