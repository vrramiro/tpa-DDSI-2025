package ar.utn.dssi.FuenteEstatica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.ILectorDeArchivos;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.impl.FactoryLector;
import ar.utn.dssi.FuenteEstatica.models.entities.normalizadorAdapter.INormalizadorAdapter;
import ar.utn.dssi.FuenteEstatica.models.errores.RepositorioVacio;
import ar.utn.dssi.FuenteEstatica.models.errores.ValidacionException;
import ar.utn.dssi.FuenteEstatica.models.repositories.IHechosRepositorio;
import ar.utn.dssi.FuenteEstatica.services.impl.HechoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class HechoTest {

  private IHechosRepositorio hechoRepositorio;
  private INormalizadorAdapter normalizadorAdapter;
  private HechoServicio hechoServicio;

  @BeforeEach
  void setUp() {
    hechoRepositorio = Mockito.mock(IHechosRepositorio.class);
    normalizadorAdapter = Mockito.mock(INormalizadorAdapter.class);

    hechoServicio = new HechoServicio(normalizadorAdapter);
    ReflectionTestUtils.setField(hechoServicio, "hechoRepositorio", hechoRepositorio);
    ReflectionTestUtils.setField(hechoServicio, "normalizadorAdapter", normalizadorAdapter);
    ReflectionTestUtils.setField(hechoServicio, "cantidadMinimaDeHechos", 0);
  }

  @Test
  void obtenerHechos_ConHechos_RetornaListaDTOs() {
    Hecho hecho = new Hecho();
    hecho.setId(1L);
    hecho.setTitulo("Hecho de prueba");

    when(hechoRepositorio.findAll()).thenReturn(List.of(hecho));
    when(hechoRepositorio.save(any())).thenReturn(hecho);

    List<HechoOutputDTO> resultado = hechoServicio.obtenerHechos(LocalDateTime.now());

    assertEquals(1, resultado.size());
    assertEquals("Hecho de prueba", resultado.get(0).getTitulo());
    verify(hechoRepositorio, times(1)).findAll();
    verify(hechoRepositorio, times(1)).save(any(Hecho.class));
  }

  @Test
  void obtenerHechos_SinHechos_LanzaRepositorioVacio() {
    // Arrange
    when(hechoRepositorio.findAll()).thenReturn(new ArrayList<>());

    // Act & Assert
    assertThrows(RepositorioVacio.class, () -> hechoServicio.obtenerHechos(LocalDateTime.now()));
  }

  @Test
  void importarArchivo_HechoNormalizado_GuardaEnRepositorio() {
    Hecho hechoOriginal = new Hecho();
    hechoOriginal.setTitulo("Original");

    Hecho hechoNormalizado = new Hecho();
    hechoNormalizado.setTitulo("Normalizado");

    when(normalizadorAdapter.obtenerHechoNormalizado(any()))
        .thenReturn(Mono.just(hechoNormalizado));

    when(hechoRepositorio.saveAll(any())).thenReturn(List.of(hechoNormalizado));

    ILectorDeArchivos lectorMock = mock(ILectorDeArchivos.class);
    when(lectorMock.importarHechos(any())).thenReturn(List.of(hechoOriginal));

    FactoryLector factoryMock = mock(FactoryLector.class);
    when(factoryMock.crearLector(any())).thenReturn(lectorMock);

    ReflectionTestUtils.setField(hechoServicio, "factoryLector", factoryMock);


    hechoServicio.importarArchivo(new File("dummy.csv"));

    verify(hechoRepositorio, times(1)).saveAll(any());
    verify(normalizadorAdapter, times(1)).obtenerHechoNormalizado(hechoOriginal);
  }

  @Test
  void importarArchivo_MenosQueMinimos_LanzaValidacionException() {
    File archivo = new File("dummy.csv");

    ILectorDeArchivos lectorMock = mock(ILectorDeArchivos.class);
    when(lectorMock.importarHechos(any())).thenReturn(List.of());

    FactoryLector factoryMock = mock(FactoryLector.class);
    when(factoryMock.crearLector(any())).thenReturn(lectorMock);

    ReflectionTestUtils.setField(hechoServicio, "factoryLector", factoryMock);
    ReflectionTestUtils.setField(hechoServicio, "cantidadMinimaDeHechos", 5);

    assertThrows(ValidacionException.class, () -> hechoServicio.importarArchivo(archivo));
  }

}