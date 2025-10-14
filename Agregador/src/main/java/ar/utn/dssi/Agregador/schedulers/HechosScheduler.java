package ar.utn.dssi.Agregador.schedulers;

import ar.utn.dssi.Agregador.services.IHechosService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HechosScheduler {
  private final IHechosService hechosService;

  @Scheduled(cron = "${cron.hechos}")
  public void actualizarHechos() {
    hechosService.importarNuevosHechos();
  }
}
