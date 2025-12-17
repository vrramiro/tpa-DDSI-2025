package ar.utn.dssi.Agregador.schedulers;

import ar.utn.dssi.Agregador.services.IConsensoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsensoScheduler {
  private final IConsensoService consensoService;

  @Scheduled(cron = "${cron.consenso}")
  public void consensuarHechos() {
    log.info("Consensuando hechos...");
    consensoService.consensuarHechos();
  }
}
