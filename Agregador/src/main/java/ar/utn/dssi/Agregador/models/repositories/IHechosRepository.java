package ar.utn.dssi.Agregador.modelos.repositorio;

import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHechosRepository {
    public Hecho findById(Long idHecho);
    public Hecho save(Hecho hecho);
    public List<Hecho> findall();
    public long obtenerUltimoId();
}