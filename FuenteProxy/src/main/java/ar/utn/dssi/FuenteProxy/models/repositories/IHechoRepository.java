package ar.utn.dssi.FuenteProxy.models.repositories;

import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface IHechoRepository extends JpaRepository<Hecho, Long> {
  List<Hecho> findByEliminadoFalseAndFechaCargaIsAfter(LocalDateTime fechaCarga);

  List<Hecho> findByIdExternoInAndFuenteIdIn(Set<Integer> idsExternos, Set<Long> idsFuentes);
}
