package ar.utn.dssi.FuenteProxy.schedulers;

import ar.utn.dssi.FuenteProxy.service.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HechosScheduler {

  @Autowired
  private IHechosService hechosService;

  @Scheduled(cron = "${cron.hechosImportar}")
  public void importarHechos() {
    hechosService.importarHechos();
  }
}

