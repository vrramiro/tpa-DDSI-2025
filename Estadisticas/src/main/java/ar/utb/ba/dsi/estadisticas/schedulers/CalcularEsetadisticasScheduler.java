package ar.utb.ba.dsi.estadisticas.schedulers;

import ar.utb.ba.dsi.estadisticas.services.IEstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CalcularEsetadisticasScheduler {
    @Autowired
    private IEstadisticasService estadisticasService;

    @Scheduled(cron = "${cron.estadisticas}")
    public void calcularEsetadisticas() {
        estadisticasService.calcularEstadisticas();
    }
}
