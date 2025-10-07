package ar.utn.dssi.Agregador.event.listeners;

import ar.utn.dssi.Agregador.error.ColeccionNoEncontrada;
import ar.utn.dssi.Agregador.event.ColeccionDesactualizadaEvent;
import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ColeccionDesactualizadaListener {
  private final IColeccionRepository coleccionRepository;
  private final IHechosRepository hechosRepository;

  @Async
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onColeccionDesactualizadaEvent(ColeccionDesactualizadaEvent event) {
    String handleColeccion = event.handleColeccionDesactualizada();

    Coleccion coleccionDesactualizada = this.coleccionRepository
        .findColeccionByHandle(handleColeccion)
        .orElseThrow(() -> new ColeccionNoEncontrada(handleColeccion));

    List<Hecho> hechos = this.hechosRepository.findAll();
    coleccionDesactualizada.agregarHechos(hechos);

    this.coleccionRepository.save(coleccionDesactualizada);
  }
}
