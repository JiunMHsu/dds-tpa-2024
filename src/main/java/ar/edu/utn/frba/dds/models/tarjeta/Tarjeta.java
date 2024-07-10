package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.personaVulnerable.PersonaVulnerable;
import java.time.LocalTime;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Tarjeta {
  private String codigo;
  private PersonaVulnerable persona;
  private Integer usosDia;
  private Integer usosPorDia;
  private LocalTime horaMedianoche;
  private ArrayList<RegistroTarjetas> registro;

  public static Tarjeta with(String codigo, PersonaVulnerable persona, Integer usosDia) {
    return Tarjeta
        .builder()
        .codigo(codigo)
        .persona(persona)
        .usosDia(usosDia)
        .registro(new ArrayList<>())
        .build();
  }

  public static Tarjeta with(String codigo, PersonaVulnerable persona) {
    return Tarjeta
        .builder()
        .codigo(codigo)
        .persona(persona)
        .registro(new ArrayList<>())
        .build();
  }

  public static Tarjeta with(String codigo) {
    return Tarjeta
        .builder()
        .codigo(codigo)
        .registro(new ArrayList<>())
        .build();
  }

  public static Tarjeta with(PersonaVulnerable persona, Integer usosDia) {
    return Tarjeta
        .builder()
        .persona(persona)
        .usosDia(usosDia)
        .registro(new ArrayList<>())
        .build();
  }

  public static Tarjeta with(PersonaVulnerable persona, Integer usosDia, LocalTime horaMedianoche) {
    return Tarjeta
        .builder()
        .persona(persona)
        .usosDia(usosDia)
        .horaMedianoche(horaMedianoche)
        .registro(new ArrayList<>())
        .build();
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
    usosPorDia = 4 + persona.getMenoresACargo() * 2;
  }
}
