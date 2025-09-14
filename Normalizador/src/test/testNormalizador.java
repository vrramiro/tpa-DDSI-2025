package ar.utb.ba.dsi.Normalizador.controllers;

import ar.utb.ba.dsi.Normalizador.services.UbicacionService;
import ar.utb.ba.dsi.Normalizador.models.dtos.UbicacionOutputDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UbicacionController.class)
class UbicacionControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Cliente simulado para hacer requests HTTP al controller

    @MockBean
    private UbicacionService ubicacionService; // Servicio simulado

    @Test
    void obtenerUbicacion_retornaOk() throws Exception {
        // Datos de prueba
        UbicacionOutputDTO ubicacionEsperada = new UbicacionOutputDTO();
        ubicacionEsperada.setProvincia("Buenos Aires");
        ubicacionEsperada.setCiudad("La Plata");

        // Configuración del comportamiento simulado del servicio
        when(ubicacionService.obtenerUbicacionOutPut(-34.9214, -57.9544))
                .thenReturn(ubicacionEsperada);

        // Ejecución del request y validaciones
        mockMvc.perform(get("/ubicacion/normalizar")
                        .param("latitud", "-34.9214")
                        .param("longitud", "-57.9544")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.provincia").value("Buenos Aires"))
                .andExpect(jsonPath("$.ciudad").value("La Plata"));
    }

    @Test
    void obtenerUbicacion_retornaBadRequest_siFaltanParametros() throws Exception {
        mockMvc.perform(get("/ubicacion/normalizar")
                        .param("latitud", "-34.9214")) // falta "longitud"
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerUbicacion_retornaInternalServerError_siFallaElServicio() throws Exception {
        // Configuración para que el servicio lance una excepción
        doThrow(new RuntimeException("Fallo interno"))
                .when(ubicacionService).obtenerUbicacionOutPut(-34.9214, -57.9544);

        // Ejecución del request y validaciones
        mockMvc.perform(get("/ubicacion/normalizar")
                        .param("latitud", "-34.9214")
                        .param("longitud", "-57.9544"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Fallo interno")));
    }
}
