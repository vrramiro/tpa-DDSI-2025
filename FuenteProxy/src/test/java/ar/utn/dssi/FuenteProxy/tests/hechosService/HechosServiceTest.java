package ar.utn.dssi.FuenteProxy.tests.hechosService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.utn.dssi.FuenteProxy.dto.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.entities.Categoria;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.Ubicacion;
import ar.utn.dssi.FuenteProxy.models.entities.normalizador.INormalizadorAdapter;
import ar.utn.dssi.FuenteProxy.error.HechoNoEcontrado;
import ar.utn.dssi.FuenteProxy.models.repositories.IFuenteRepository;
import ar.utn.dssi.FuenteProxy.models.repositories.IHechoRepository;
import ar.utn.dssi.FuenteProxy.service.impl.HechosService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class HechosServiceTest {

  @Mock
  private IHechoRepository hechoRepository;

  @Mock
  private IFuenteRepository fuenteRepository;

  @Mock
  private INormalizadorAdapter normalizadorAdapter;

  @InjectMocks
  private HechosService hechosService;

  @Test
  public void deberiaRetornarHechosPosterioresALaFechaDada() {
    // Arrange - Preparamos nuestro escenario
    LocalDateTime fechaTest = LocalDateTime.of(2024, 1, 15, 10, 0);

    // Creamos algunos hechos de ejemplo para simular la respuesta de la base de datos
    Hecho hecho1 = crearHechoDeEjemplo(1L, 100, "Incendio forestal");
    Hecho hecho2 = crearHechoDeEjemplo(2L, 200, "Terremoto");
    List<Hecho> hechosEsperados = new ArrayList<>();

    hechosEsperados.add(hecho1);
    hechosEsperados.add(hecho2);

    // Le decimos a nuestro mock qué debe devolver cuando se llame al método específico
    when(hechoRepository.findByEliminadoFalseAndFechaCargaIsAfter(fechaTest))
        .thenReturn(hechosEsperados);

    // Act - Ejecutamos el método que queremos probar
    List<HechoOutputDTO> resultado = hechosService.obtenerHechos(fechaTest);

    // Assert - Verificamos que todo funcionó como esperábamos
    assertThat(resultado).hasSize(2);
    assertThat(resultado.get(0).getIdHecho()).isEqualTo(1L);
    assertThat(resultado.get(1).getIdHecho()).isEqualTo(2L);

    // Verificamos que el repositorio fue llamado exactamente una vez con el parámetro correcto
    verify(hechoRepository, times(1))
        .findByEliminadoFalseAndFechaCargaIsAfter(fechaTest);
  }

  @Test
  public void deberiaRetornarUnaListaVacia() {
    // Arrange - Preparamos nuestro escenario
    LocalDateTime fechaTest = LocalDateTime.of(2024, 1, 15, 10, 0);

    List<Hecho> hechosEsperados = new ArrayList<>();

    // Le decimos a nuestro mock qué debe devolver cuando se llame al método específico
    when(hechoRepository.findByEliminadoFalseAndFechaCargaIsAfter(fechaTest))
        .thenReturn(hechosEsperados);

    List<HechoOutputDTO> resultado = hechosService.obtenerHechos(fechaTest);

    assertThat(resultado).isEmpty();

    // Verificamos que el repositorio fue llamado exactamente una vez con el parámetro correcto
    verify(hechoRepository, times(1))
        .findByEliminadoFalseAndFechaCargaIsAfter(fechaTest);
  }

  @Test
  public void deberiaLanzarExcepcionCuandoLaFechaEsNull() {
    // Verificamos que se lance la excepción correcta con el mensaje esperado
    assertThatThrownBy(() -> hechosService.obtenerHechos(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Error al cargar la fecha de ultima comunicacion");

    // Verificamos que nunca se llame al repositorio cuando la fecha es inválida
    verify(hechoRepository, never()).findByEliminadoFalseAndFechaCargaIsAfter(any());
  }

  @Test
  public void deberiaEliminarseElHechoIndicado() {
    // Creamos algunos hechos de ejemplo para simular la respuesta de la base de datos
    Hecho hecho1 = crearHechoDeEjemplo(1L, 100, "Incendio forestal");
    Hecho hecho2 = crearHechoDeEjemplo(2L, 200, "Terremoto");
    List<Hecho> hechosEsperados = new ArrayList<>();

    hechosEsperados.add(hecho1);
    hechosEsperados.add(hecho2);

    Long idHecho = 1L;

    when(hechoRepository.findById(idHecho)).thenReturn(Optional.of(hecho1));
    when(hechoRepository.save(any(Hecho.class))).thenReturn(hecho1);

    hechosService.eliminarHecho(1L);

    assertThat(hechosEsperados).hasSize(2);
    assertThat(hechosEsperados.get(0).getEliminado()).isTrue();
    assertThat(hechosEsperados.get(1).getEliminado()).isFalse();
  }

  @Test
  public void hechoEliminadoNoSeDevuelve() {
    // Creamos algunos hechos de ejemplo para simular la respuesta de la base de datos
    Hecho hecho1 = crearHechoDeEjemplo(1L, 100, "Incendio forestal");
    Hecho hecho2 = crearHechoDeEjemplo(2L, 200, "Terremoto");
    List<Hecho> hechosAntes = new ArrayList<>();

    hechosAntes.add(hecho1);
    hechosAntes.add(hecho2);

    Long idHecho = 1L;

    LocalDateTime fechaTest = LocalDateTime.of(2024, 1, 15, 10, 0);

    List<Hecho> hechosDespues = new ArrayList<>();

    hechosDespues.add(hecho2);

    when(hechoRepository.findById(idHecho)).thenReturn(Optional.of(hecho1));
    when(hechoRepository.save(any(Hecho.class))).thenReturn(hecho1);
    when(hechoRepository.findByEliminadoFalseAndFechaCargaIsAfter(any()))
        .thenReturn(hechosAntes)
        .thenReturn(hechosDespues);

    List<HechoOutputDTO> resultadoAntes = hechosService.obtenerHechos(fechaTest);

    hechosService.eliminarHecho(1L);

    List<HechoOutputDTO> resultadoDespues = hechosService.obtenerHechos(fechaTest);

    assertThat(resultadoDespues.size()).isEqualTo(1);
    assertThat(resultadoDespues.get(0).getIdHecho()).isEqualTo(2L);
  }

  @Test
  public void deberiaLanzarExcepcionCuandoElHechoNoExiste() {
    Long idHecho = 1L;

    when(hechoRepository.findById(idHecho)).thenReturn(Optional.empty());

    // Verificamos que se lance la excepción correcta con el mensaje esperado
    assertThatThrownBy(() -> hechosService.eliminarHecho(idHecho))
        .isInstanceOf(HechoNoEcontrado.class)
        .hasMessage("No se encontró el hecho con id: " + idHecho);

    // Verificamos que nunca se llame al repositorio cuando el hecho no existe
    verify(hechoRepository, times(1)).findById(idHecho);
    verify(hechoRepository, never()).save(any());
  }

  @Test
  public void deberiaDevolverListaConHechosImportadosNuevos() {
    List<Hecho> hechosNuevos = new ArrayList<>();
  }

  // Método auxiliar para crear hechos de prueba de manera consistente
  private Hecho crearHechoDeEjemplo(Long id, Integer idExterno, String titulo) {
    Hecho hecho = new Hecho();
    hecho.setId(id);
    hecho.setIdExterno(idExterno);
    hecho.setTitulo(titulo);
    hecho.setDescripcion("Descripción de " + titulo);

    // Configuramos la categoría
    Categoria categoria = new Categoria();
    categoria.setNombre("Desastre Natural");
    hecho.setCategoria(categoria);

    // Configuramos la ubicación
    Ubicacion ubicacion = new Ubicacion();
    ubicacion.setLatitud(-34.6037);
    ubicacion.setLongitud(-58.3816);
    hecho.setUbicacion(ubicacion);

    hecho.setFechaAcontecimiento(LocalDateTime.now().minusDays(1));
    hecho.setFechaCarga(LocalDateTime.now());
    hecho.setEliminado(false);

    return hecho;
  }
}
