package ar.utn.dssi.Agregador.schedulers;

import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HechosScheduler {
  @Autowired
  private IHechosService hechosService;

  @Scheduled(cron = "${cron.hechos}")
  public void actualizarHechos(){
    hechosService.importarNuevosHechos();
  }
}
