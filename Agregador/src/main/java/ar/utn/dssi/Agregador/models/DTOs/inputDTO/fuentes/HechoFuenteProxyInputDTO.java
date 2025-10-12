package ar.utn.dssi.Agregador.models.DTOs.inputDTO.fuentes;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.CategoriaInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.UbicacionInputDTO;
import ar.utn.dssi.Agregador.models.entities.Categoria;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HechoFuenteProxyInputDTO {
    private Long idOrigen;
    private String titulo;
    private String descripcion;
    private String tituloSanitizado;
    private String descripcionSanitizada;

    private CategoriaInputDTO categoria;
    private UbicacionInputDTO ubicacion;

    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;

    //private String consenso;
    //private String autor;
}
