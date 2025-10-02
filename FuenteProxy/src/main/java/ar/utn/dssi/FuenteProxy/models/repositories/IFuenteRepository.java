package ar.utn.dssi.FuenteProxy.models.repositories;

import ar.utn.dssi.FuenteProxy.models.entities.fuentes.Fuente;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.TipoFuente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFuenteRepository extends JpaRepository<Fuente, Long> {
    List<Fuente> findFuentesByTipoFuenteNot(TipoFuente tipo);
    List<Fuente> findFuentesByTipoFuente(TipoFuente tipo);
}
