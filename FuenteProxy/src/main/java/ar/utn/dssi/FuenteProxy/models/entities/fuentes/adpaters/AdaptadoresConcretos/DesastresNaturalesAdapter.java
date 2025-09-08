package ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.AdaptadoresConcretos;

import ar.utn.dssi.FuenteProxy.models.DTOs.external.DesastresNaturales.HechoDesastresNaturales;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.TipoFuente;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.Apis.DesastresNaturalesAPI;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.IServicioExternoAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.Categoria;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.Ubicacion;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

// -------------------- ADAPTER --------------------
@Component
public class DesastresNaturalesAdapter implements IServicioExternoAdapter {

    private final DesastresNaturalesAPI api;

    public DesastresNaturalesAdapter(DesastresNaturalesAPI api) {
        this.api = api;
    }

    @Override
    public Mono<List<Hecho>> obtenerHechos() {
        return api.autenticar("ddsi@gmail.com", "ddsi2025*")
                .flatMapMany(token -> obtenerTodasLasPaginas(token, 1))
                .map(this::mapearHecho)
                .collectList();
    }

    @Override
    public TipoFuente getTipoFuente() {
        return TipoFuente.DESASTRES_NATURALES;
    }

    private Flux<HechoDesastresNaturales> obtenerTodasLasPaginas(String token, int pagina) {
        return api.obtenerHechosPorPagina(token, pagina, 100)
                .flatMapMany(hechos -> {
                    if (hechos.getHechosObtenidos().isEmpty()) {
                        return Flux.empty();
                    }
                    Flux<HechoDesastresNaturales> actuales = Flux.fromIterable(hechos.getHechosObtenidos());
                    if (hechos.getCurrent_page() >= hechos.getLast_page()) {
                        return actuales;
                    }
                    return actuales.concatWith(obtenerTodasLasPaginas(token, pagina + 1));
                });
    }

    private Hecho mapearHecho(HechoDesastresNaturales hechoObtenido) {
        Hecho hecho = new Hecho();

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setLatitud(hechoObtenido.getLatitud());
        ubicacion.setLongitud(hechoObtenido.getLongitud());

        Categoria categoria = new Categoria();
        categoria.setNombre(hechoObtenido.getCategoria());

        hecho.setTitulo(hechoObtenido.getTitulo());
        hecho.setDescripcion(hechoObtenido.getDescripcion());
        hecho.setCategoria(categoria);
        hecho.setUbicacion(ubicacion);
        hecho.setFechaAcontecimiento(hechoObtenido.getFecha_hecho());
        hecho.setFechaCarga(hechoObtenido.getCreated_at());

        return hecho;
    }
}


/*public class DesastresNaturalesAdapter implements IServicioExternoAdapter {
  private final WebClient webClient;
  private String token;

  public DesastresNaturalesAdapter() {
    this.webClient = WebClient.builder()
        .baseUrl("https://api-ddsi.disilab.ar/public") //TODO Agregar al config
        .build();
  }

  public Mono<List<Hecho>> obtenerHechos(){
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
}/*
