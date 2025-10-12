package ar.utn.dssi.Agregador.event.listeners;

import ar.utn.dssi.Agregador.error.ColeccionNoEncontrada;
import ar.utn.dssi.Agregador.event.ColeccionDesactualizadaEvent;
import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ColeccionDesactualizadaListener {
  private final IColeccionRepository coleccionRepository;
  private final IHechosRepository hechosRepository;

  @Async
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onColeccionDesactualizadaEvent(ColeccionDesactualizadaEvent event) {
    String handleColeccion = event.handleColeccionDesactualizada();

    Coleccion coleccionDesactualizada = this.coleccionRepository
        .findColeccionByHandle(handleColeccion)
        .orElseThrow(() -> new ColeccionNoEncontrada(handleColeccion));

    List<Hecho> hechos = this.hechosRepository.findAll();
    coleccionDesactualizada.liberarHechos();
    coleccionDesactualizada.agregarHechos(hechos);
    coleccionDesactualizada.marcarComoActualizada();

    log.info("Colección con handle {} ha sido actualizada con {} hechos.", handleColeccion, coleccionDesactualizada.getHechos().size());

    this.coleccionRepository.save(coleccionDesactualizada);
  }
}
