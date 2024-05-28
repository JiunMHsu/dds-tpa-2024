package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.usuario.PersonaVulnerable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Tarjeta {
  private String codigo; // Agregar generador
  private PersonaVulnerable persona;
  private Integer usosDia;
  private Integer usosPorDia;
  private LocalTime horaMedianoche = LocalTime.of(0,0,0);
  private List<RegistroTarjetas> registro = new ArrayList<>();

  public Boolean puedeUsar(){
    if (usosDia > 0) {
      usosDia--;
      return true;
    } else {
      return false;
    }
  }
  public void registrarUsoTarjeta(Heladera heladera){
    if(puedeUsar()){
      RegistroTarjetas nuevoRegistro = new RegistroTarjetas(heladera);
      registro.add(nuevoRegistro);
    }
  }
  public void verificarMedianoche() {
    if (LocalTime.now().equals(horaMedianoche)) {
      usosDia = usosPorDia;
    }
  }
}
