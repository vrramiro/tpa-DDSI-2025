package ar.utn.dssi.Agregador.mappers;

public class MapperDeFiltros {
  /*public static Filtro filtro(FiltroInputDTO filtroInput, Categoria categoria) {
    Filtro filtro = new Filtro();

    if(categoria != null) {
      filtro.setCategoria(categoria);
    }

    if(filtroInput.getFechaSubidaHasta() != null) {
      filtro.setFechaSubidaHasta(LocalDate.parse(filtroInput.getFechaSubidaHasta()));
    }

    if(filtroInput.getFechaAcontecimientoHasta() != null) {
      filtro.setFechaAcontecimientoHasta(LocalDate.parse(filtroInput.getFechaAcontecimientoHasta()));
    }

    if(filtroInput.getFechaAcontecimientoDesde() != null) {
      filtro.setFechaAcontecimientoDesde(LocalDate.parse(filtroInput.getFechaAcontecimientoDesde()));
    }

    if(filtroInput.getFechaSubidaDesde() != null) {
      filtro.setFechaSubidaDesde(LocalDate.parse(filtroInput.getFechaSubidaDesde()));
    }

    if(filtroInput.getLongitud() != null && filtroInput.getLatitud() != null) {
      Ubicacion ubicacion = new Ubicacion(filtroInput.getLatitud(), filtroInput.getLongitud());
      filtro.setUbicacion(ubicacion);
    }

    return filtro;
  }*/ //TODO: REVISAR? LOS FILTROS NO TIENEN ESTOS DATOS
}
