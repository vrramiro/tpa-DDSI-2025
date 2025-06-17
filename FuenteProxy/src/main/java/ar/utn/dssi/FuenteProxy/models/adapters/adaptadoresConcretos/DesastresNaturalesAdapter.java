package ar.utn.dssi.FuenteProxy.models.adapters.adaptadoresConcretos;

import ar.utn.dssi.FuenteProxy.models.DTOs.external.DesastresNaturales.DatosLogin;
import ar.utn.dssi.FuenteProxy.models.DTOs.external.DesastresNaturales.HechoDesastresNaturales;
import ar.utn.dssi.FuenteProxy.models.DTOs.external.DesastresNaturales.HechosDesastresNaturales;
import ar.utn.dssi.FuenteProxy.models.DTOs.external.DesastresNaturales.RespuestaLogin;
import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.adapters.IServicioExternoAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.Categoria;
import ar.utn.dssi.FuenteProxy.models.entities.Origen;
import ar.utn.dssi.FuenteProxy.models.entities.Ubicacion;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.List;

public class DesastresNaturalesAdapter implements IServicioExternoAdapter {
  private final WebClient webClient;
  private String token;

  public DesastresNaturalesAdapter() {
    this.webClient = WebClient.builder()
        .baseUrl("https://api-ddsi.disilab.ar/public") //TODO Agregar al config
        .build();
  }

  public List<HechoOutputDTO> obtenerHechos(){
    return this
        .obtenerHechosDeAPI()
        .stream()
        .map(hechoObtenido -> {
          HechoOutputDTO dto = new HechoOutputDTO();

          Ubicacion ubicacion = new Ubicacion();
          ubicacion.setLatitud(hechoObtenido.getLatitud());
          ubicacion.setLongitud(hechoObtenido.getLongitud());

          Categoria categoria = new Categoria();
          categoria.setNombre(hechoObtenido.getCategoria());

          dto.setTitulo(hechoObtenido.getTitulo());
          dto.setDescripcion(hechoObtenido.getDescripcion());
          dto.setCategoria(categoria);
          dto.setUbicacion(ubicacion);
          dto.setFechaAcontecimiento(hechoObtenido.getFecha_hecho());
          dto.setFechaCarga(hechoObtenido.getCreated_at());
          dto.setOrigen(Origen.FUENTE_PROXY);

          return dto;
        })
        .toList();
  }

  private void autenticarse() {
    //TODO por seguridad no deberia ser asi pero por simplicidad lo dejamos asi por ahora
    //Datos irian en el properties y no deberia estar commiteado en el repo
    DatosLogin datosLogin = new DatosLogin("ddsi@gmail.com", "ddsi2025*");

    RespuestaLogin respuesta = webClient
        .post()
        .uri(uriBuilder -> uriBuilder.path("/auth/login").build())
        .bodyValue(datosLogin)
        .retrieve()
        .bodyToMono(RespuestaLogin.class)
        .block();

    this.token = respuesta.getData().getAccess_token();
  }

  private List<HechoDesastresNaturales> obtenerHechosDeAPI() {
    this.autenticarse();

    Integer pagina = 1;
    List<HechoDesastresNaturales> hechosObtenidos = new ArrayList<HechoDesastresNaturales>();

    while(true) {
      HechosDesastresNaturales hechosPedidos = hechosPorPagina(pagina);

      //Cuando pedimos hechos a una pagina que no tiene hechos la API devuelve una lista vacia
      if(hechosPedidos.getHechosObtenidos().isEmpty()) break;

      hechosObtenidos.addAll(hechosPedidos.getHechosObtenidos());

      if(hechosPedidos.getCurrent_page() >= hechosPedidos.getLast_page()) break;

      pagina++;
    }

    return hechosObtenidos;
  }

  private HechosDesastresNaturales hechosPorPagina(Integer pagina) {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/desastres")
            .queryParam("page", pagina)
            .queryParam("per_page", 100)
            .build())
        .header("Authorization", "Bearer " + token)
        .retrieve()
        .bodyToMono(HechosDesastresNaturales.class)
        .block();
  }
}
