package ar.utn.dssi.FuenteProxy.models.repositories;

import ar.utn.dssi.FuenteProxy.models.entities.fuentes.Fuente;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.TipoFuente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFuenteRepository extends JpaRepository<Fuente, Long> {
    List<Fuente> findTipoNot(TipoFuente tipo);
    List<Fuente> findTipo(TipoFuente tipo);
}
