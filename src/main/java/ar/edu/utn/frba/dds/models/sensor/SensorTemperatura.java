package ar.edu.utn.frba.dds.models.sensor;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.incidente.Incidente;
import ar.edu.utn.frba.dds.models.incidente.TipoIncidente;
import java.time.LocalDateTime;

public class SensorTemperatura {
  private Heladera heladera;

  public SensorTemperatura(Heladera heladera) {
    this.heladera = heladera;
  }

  // TODO
  public void recibirTemperatura(Double temperatura) {
    if (!heladera.getRangoTemperatura().incluye(temperatura)) {
      this.lanzarAlertaTemperatura();
    }

    this.cronometrar(); // no es correcto esta implementación
  }

  // TODO
  private void cronometrar() {
    // debería cronometrar, y si tras un tiempo determinado
    // el sistema no recibe la temperatura, lanzar una falla de conexion

    // ...
    this.lanzarFallaConexion();
  }

  private void lanzarAlertaTemperatura() {
    Incidente.reportar(TipoIncidente.FALLA_TEMPERATURA, heladera, LocalDateTime.now());
  }

  private void lanzarFallaConexion() {
    Incidente.reportar(TipoIncidente.FALLA_CONEXION, heladera, LocalDateTime.now());
  }
}
