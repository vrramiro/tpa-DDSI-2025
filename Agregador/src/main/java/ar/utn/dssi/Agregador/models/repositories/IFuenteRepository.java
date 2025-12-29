package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoFuente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IFuenteRepository extends JpaRepository<Fuente, Long> {
  List<Fuente> findByTipoFuente(ITipoFuente tipoFuente);

  @Query(value = "SELECT * FROM fuente f WHERE f.tipo_fuente IN (:tipos)", nativeQuery = true)
  List<Fuente> findFuenteByTipoFuenteIn(@Param("tipos") List<String> tiposFuente);
}