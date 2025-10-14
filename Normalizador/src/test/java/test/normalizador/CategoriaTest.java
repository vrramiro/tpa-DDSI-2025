package test.normalizador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.utb.ba.dsi.Normalizador.dto.Input.CategoriaInputDTO;
import ar.utb.ba.dsi.Normalizador.dto.output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;
import ar.utb.ba.dsi.Normalizador.models.repositories.ICategoriaRepository;
import ar.utb.ba.dsi.Normalizador.service.impl.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.List;

class CategoriaTest {

  private ICategoriaRepository categoriaRepository;
  private CategoriaService categoriaService;

  @BeforeEach
  void setUp() {
    categoriaRepository = Mockito.mock(ICategoriaRepository.class);
    categoriaService = new CategoriaService(categoriaRepository);
  }

  @Test
  void normalizarCategoria_ExisteCategoria_RetornaCategoria() {
    // Arrange
    Categoria categoriaInput = new Categoria();
    categoriaInput.setNombre("Tecnologia");

    Categoria categoriaMock = new Categoria();
    categoriaMock.setId(1L);
    categoriaMock.setNombre("Tecnologia");

    when(categoriaRepository.findCategoriaByCategoriaExterna("tecnologia"))
        .thenReturn(categoriaMock);

    // Act
    Categoria resultado = categoriaService.normalizarCategoria(categoriaInput);

    // Assert
    assertNotNull(resultado);
    assertEquals(1L, resultado.getId());
    assertEquals("Tecnologia", resultado.getNombre());
    verify(categoriaRepository, times(1))
        .findCategoriaByCategoriaExterna("tecnologia");
  }

  @Test
  void normalizarCategoria_NoExisteCategoria_LanzaExcepcion() {
    // Arrange
    Categoria categoriaInput = new Categoria();
    categoriaInput.setNombre("Inexistente");

    when(categoriaRepository.findCategoriaByCategoriaExterna("inexistente"))
        .thenReturn(null);

    // Act & Assert
    RuntimeException ex = assertThrows(RuntimeException.class,
        () -> categoriaService.normalizarCategoria(categoriaInput));

    assertEquals("Categoria no encontrada", ex.getMessage());
  }

  @Test
  void obtenerCategorias_DevuelveListaCategorias() {
    // Arrange
    Categoria categoria1 = new Categoria();
    categoria1.setId(1L);
    categoria1.setNombre("Ropa");

    Categoria categoria2 = new Categoria();
    categoria2.setId(2L);
    categoria2.setNombre("Calzado");

    when(categoriaRepository.findAll())
        .thenReturn(Arrays.asList(categoria1, categoria2));

    // Act
    List<CategoriaOutputDTO> resultado = categoriaService.obtenerCategorias();

    // Assert
    assertEquals(2, resultado.size());
    assertEquals("Ropa", resultado.get(0).getCategoria());
    assertEquals("Calzado", resultado.get(1).getCategoria());
    verify(categoriaRepository, times(1)).findAll();
  }

  @Test
  void normalizarCategoriaOutPut_OK() {
    // Arrange
    CategoriaInputDTO inputDTO = new CategoriaInputDTO();
    inputDTO.setCategoriaExterna("Tecnologia");

    Categoria categoriaMock = new Categoria();
    categoriaMock.setId(1L);
    categoriaMock.setNombre("Tecnologia");

    when(categoriaRepository.findCategoriaByCategoriaExterna("tecnologia"))
        .thenReturn(categoriaMock);

    // Act
    CategoriaOutputDTO resultado = categoriaService.normalizarCategoriaOutPut(inputDTO);

    // Assert
    assertNotNull(resultado);
    assertEquals("Tecnologia", resultado.getCategoria());
  }
}
