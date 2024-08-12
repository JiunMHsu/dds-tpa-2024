package ar.edu.utn.frba.dds.models.sensor;

import ar.edu.utn.frba.dds.broker.SuscriptorMqtt;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.incidente.Incidente;
import ar.edu.utn.frba.dds.models.incidente.TipoIncidente;
import java.time.LocalDateTime;

public class SensorTemperatura implements SuscriptorMqtt {

  private final Heladera heladera;

  public SensorTemperatura(Heladera heladera) {
    super();
    this.heladera = heladera;
    this.suscribir(heladera.getTopicLectorTarjeta());
  }

  @Override
  public void recibirMensaje(String mensaje) {
    Double temperatura = Double.valueOf(mensaje);

    if (!heladera.getRangoTemperatura().incluye(temperatura)) {
      this.lanzarAlertaTemperatura();
    }

    // Tema de cronometrar
  }

  // TODO
  private void cronometrar() {
    // debería cronometrar, y si tras un tiempo determinado
    // el sistema no recibe la temperatura, lanzar una falla de conexion

    // ...
    this.lanzarFallaConexion();
  }

  private void lanzarAlertaTemperatura() {
    Incidente fallaTemperatura = Incidente.of(TipoIncidente.FALLA_TEMPERATURA, heladera, LocalDateTime.now());
    fallaTemperatura.reportar();
  }

  private void lanzarFallaConexion() {
    Incidente fallaConexion = Incidente.of(TipoIncidente.FALLA_CONEXION, heladera, LocalDateTime.now());
    fallaConexion.reportar();
  }


}
