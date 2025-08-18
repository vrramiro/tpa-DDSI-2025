package ar.utn.dssi.Agregador.schedulers;

import ar.utn.dssi.Agregador.services.IColeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ColeccionScheduler {
    @Autowired
    IColeccionService coleccionService;

    @Scheduled(cron = "${cron.coleccion}")
    public void consensuarColeccion() {
        coleccionService.realizarConsenso();
    }
}
