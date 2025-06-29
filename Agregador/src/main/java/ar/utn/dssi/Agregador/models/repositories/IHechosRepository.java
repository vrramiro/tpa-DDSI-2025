package ar.utn.dssi.Agregador.models.repositories;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHechosRepository {
    public Hecho findById(Long idHecho);
    public Hecho save(Hecho hecho);
    public List<Hecho> findall();
    public long obtenerUltimoId();
    public void update(Hecho hecho);
    public Hecho findByIdOrigenAndIdFuente(Long idEnFuente, Long idFuente);
}