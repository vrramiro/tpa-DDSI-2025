package ar.utn.dssi.Estadisticas.schedulers;

import ar.utn.dssi.Estadisticas.services.IEstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EstadisticasScheduler {
    @Autowired
    private IEstadisticasService estadisticasService;

    @Scheduled(cron = "${cron.estadisticas}")
    public void calcularEsetadisticas() {
        estadisticasService.calcularEstadisticas();
    }
}
