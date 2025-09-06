package ar.utb.ba.dsi.Normalizador.models.DTOs.Input;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoInputDTO {
  private String titulo;
  private String descripcion;
  private String categoria;
  private Double latitud;
  private Double longitud;
  private LocalDateTime fechaAcontecimiento;
}