package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.usuario.PersonaVulnerable;
import java.time.LocalTime;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Tarjeta {
  private String codigo;
  private PersonaVulnerable persona;
  private Integer usosDia;
  private Integer usosPorDia;
  private LocalTime horaMedianoche;
  private ArrayList<RegistroTarjetas> registro;

  public Tarjeta(String codigo, PersonaVulnerable persona, Integer usosDia, Integer usosPorDia, LocalTime horaMedianoche) {
    this.codigo = codigo;
    this.persona = persona;
    this.usosDia = usosDia;
    this.usosPorDia = usosPorDia;
    this.horaMedianoche = horaMedianoche;
    this.registro = new ArrayList<>();
  }

  public Boolean puedeUsar() {
    if (usosDia > 0) {
      usosDia--;
      return true;
    } else {
      return false;
    }
  }

  public void registrarUsoTarjeta(Heladera heladera) {
    if (puedeUsar()) {
      RegistroTarjetas nuevoRegistro = new RegistroTarjetas(heladera);
      registro.add(nuevoRegistro);
    }
  }

  public void verificarMedianoche() {
    if (LocalTime.now().equals(horaMedianoche)) {
      usosDia = usosPorDia;
    }
  }

  public void calcularUsosTarjeta(Tarjeta tarjeta) {
    usosPorDia = 4 + persona.getMenoresACargo()*2;
  }
}
