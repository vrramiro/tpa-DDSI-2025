package ar.utn.dssi.FuenteProxy.models.repositories;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHechoRepository extends JpaRepository<HechoOutputDTO, Long> {
}
