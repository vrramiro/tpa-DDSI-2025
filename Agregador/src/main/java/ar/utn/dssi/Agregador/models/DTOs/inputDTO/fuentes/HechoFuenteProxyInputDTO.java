package ar.utn.dssi.Agregador.models.DTOs.inputDTO.fuentes;

import java.time.LocalDateTime;

public class HechoFuenteProxyInputDTO {
    private Long idExterno;
    private String titulo;
    private String descripcion;
    private String tituloSanitizado;
    private String descripcionSanitizada;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
    private Long idFuenteExterna;
    //private String consenso;
    //private String autor;
}
