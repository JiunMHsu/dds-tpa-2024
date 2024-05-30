package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

public class RegistroTarjetas {

  private Heladera heladera;
  private LocalDateTime horarioUsoTarjeta;
  private List<Tarjeta> tarjetasRegistradas; // hardcodeado para los test

  public RegistroTarjetas(Heladera heladera) {
    this.heladera = heladera;
    this.horarioUsoTarjeta = LocalDateTime.now();
  }
}
