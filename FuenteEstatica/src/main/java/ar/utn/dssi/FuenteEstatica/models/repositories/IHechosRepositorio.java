package ar.utn.dssi.FuenteEstatica.models.repositories;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IHechosRepositorio extends JpaRepository<Hecho, Long> {
    /*public void save(List<Hecho> hechos);
    public List<Hecho> findAll();
    public void update(Hecho hechos);*/
    public Optional<Hecho> findById(Long id);
}