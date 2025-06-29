package ar.utn.dssi.Agregador.models.DTOs.inputDTO;

import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.Origen;
import ar.utn.dssi.Agregador.models.entities.Ubicacion;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoInputDTO {
    private Long idEnFuente;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Ubicacion ubicacion;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
    private Origen origen;
    private List<MultipartFile> contenidoMultimedia;
}

