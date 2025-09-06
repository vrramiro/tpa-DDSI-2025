package ar.utn.dssi.FuenteEstatica.models.repositories.implementation;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import ar.utn.dssi.FuenteEstatica.models.repositories.IHechosRepositorio;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/*@Repository
public class HechoRepositorio implements IHechosRepositorio {
    private List<Hecho> hechos = new ArrayList<>();

    @Override
    public void save(List<Hecho> hechosImportados) {
        hechos.addAll(hechosImportados);
    }

    @Override
    public List<Hecho> findAll() {
        return this.hechos;
    }

    @Override
    public void update(Hecho hecho) {
        hechos.remove(this.findById(hecho.getId()));
        hechos.add(hecho);
    }

    @Override
    public Optional <Hecho> findById(Long id) {
        return Optional.ofNullable(this.hechos.stream().filter(hecho -> hecho.getId().equals(id)).findFirst().orElse(null));
    }
}
*/