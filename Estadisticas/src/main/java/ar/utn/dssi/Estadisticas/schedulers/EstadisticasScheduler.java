package ar.utn.dssi.Estadisticas.schedulers;

import ar.utn.dssi.Estadisticas.services.IEstadisticasService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstadisticasScheduler {
  private final IEstadisticasService estadisticasService;

  @Scheduled(cron = "${cron.estadisticas}")
  public void calcularEsetadisticas() {
    estadisticasService.calcularEstadisticas();
  }
}
