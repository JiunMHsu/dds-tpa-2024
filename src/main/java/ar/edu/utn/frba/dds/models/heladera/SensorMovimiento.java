package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.incidente.Incidente;
import ar.edu.utn.frba.dds.models.incidente.TipoIncidente;
import java.time.LocalDateTime;

public class SensorMovimiento {
  private Heladera heladera;

  public SensorMovimiento(Heladera heladera) {
    this.heladera = heladera;
  }

  public void recibirAlertaMovimiento() {
    Incidente.reportar(TipoIncidente.FRAUDE, this.heladera, LocalDateTime.now());
  }
}
