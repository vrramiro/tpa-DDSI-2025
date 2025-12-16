package ar.utn.dssi.Agregador.schedulers;

import ar.utn.dssi.Agregador.services.IConsensoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsensoScheduler {
  private final IConsensoService consensoService;

  @Scheduled(cron = "${cron.consenso}")
  public void consensuarHechos() {
    consensoService.consensuarHechos();
  }
}
