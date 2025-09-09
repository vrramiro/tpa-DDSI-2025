package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHechosRepository extends JpaRepository<Hecho, Long> {
  List<Hecho> findByIdEnFuenteAndFuente(Long idOrigen, Fuente fuente);
}