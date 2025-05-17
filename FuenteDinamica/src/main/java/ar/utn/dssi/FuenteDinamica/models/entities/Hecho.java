package ar.utn.dssi.FuenteDinamica.models.entities;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hecho {
  private Long idHecho;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;


  //TODO: Gestionar si el usuario que lo cargo es registrado o no =>
  //TODO: Analizar tema contenido multimedia
}