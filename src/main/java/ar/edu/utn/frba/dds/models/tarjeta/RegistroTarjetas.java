package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.time.LocalDateTime;

public class RegistroTarjetas {
  private Heladera heladera;
  private LocalDateTime horarioUsoTarjeta;
  public RegistroTarjetas(Heladera heladera) {
    this.heladera = heladera;
    this.horarioUsoTarjeta = LocalDateTime.now();
  }
}
