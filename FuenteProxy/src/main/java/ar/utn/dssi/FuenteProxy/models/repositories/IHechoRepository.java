package ar.utn.dssi.FuenteProxy.models.repositories;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.Fuente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface IHechoRepository extends JpaRepository<Hecho, Long> {
  Boolean existsByIdExternoAndFuente(Integer idExterno, Fuente fuenteHecho);
  List<Hecho> findByEliminadoFalseAndFechaCargaIsAfter(LocalDateTime fechaCarga);
}
