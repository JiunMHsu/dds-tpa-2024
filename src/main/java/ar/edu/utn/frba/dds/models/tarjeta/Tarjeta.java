package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.usuario.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.tarjeta.RegistroTarjetas;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Setter
@Getter

public class Tarjeta {
  private String codigo;
  private PersonaVulnerable persona;
  private Integer usosDia;
  private Integer usosPorDia;
  private LocalTime horaMedianoche = LocalTime.of(0, 0, 0);
  private List<RegistroTarjetas> registro = new ArrayList<>();

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

  public String generadorCodigo(RegistroTarjetas registroTarjetas) {
    String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    String codigoTarjeta;

    do {
      StringBuilder codigoBuilder = new StringBuilder();
      for (int i = 0; i < 11; i++) {
        int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
        char caracterAleatorio = banco.charAt(indiceAleatorio);
        codigoBuilder.append(caracterAleatorio);
      }
      codigoTarjeta = codigoBuilder.toString();
    } while (comprobarCodigosRepetidos(codigoTarjeta, registroTarjetas));

    return codigoTarjeta;
  }

  public static int numeroAleatorioEnRango(int minimo, int maximo) {

    return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
  }

  public static Boolean comprobarCodigosRepetidos(String codigo, RegistroTarjetas registroTarjetas) {
    List<Tarjeta> tarjetasRegistradas = registroTarjetas.getTarjetasRegistradas();
    for (Tarjeta tarjeta : tarjetasRegistradas) {
      if (tarjeta.getCodigo().equals(codigo)) {
        return true;
      }
    }
    return false;
  }
}
