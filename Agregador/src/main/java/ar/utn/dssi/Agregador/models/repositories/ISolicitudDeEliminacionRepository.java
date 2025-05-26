package ar.utn.dssi.Agregador.modelos.repositorio;

public interface ISolicitudDeEliminacionRepository {
    public void findAll();
    public void findById(Long id);
    public void save();
}
