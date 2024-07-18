package ar.edu.utn.frba.dds.models.sensor;

import ar.edu.utn.frba.dds.broker.ClienteMqtt;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.incidente.Incidente;
import ar.edu.utn.frba.dds.models.incidente.TipoIncidente;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class SensorMovimiento extends ClienteMqtt {

  private Heladera heladera;

  public SensorMovimiento(Heladera heladera) {
    super();
    this.heladera = heladera;
    this.suscribir(heladera.getTopicLectorTarjeta());
  }

  @Override
  public void recibirMensaje(String mensaje) {
    Incidente alertaMovimiento = Incidente.of(TipoIncidente.FRAUDE, this.heladera, LocalDateTime.now());
    alertaMovimiento.reportar();
  }
}
