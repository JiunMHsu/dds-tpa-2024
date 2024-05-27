package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.usuario.PersonaVulnerable;
import java.time.LocalDate;

public class Tarjeta {
  private String codigo;
  private PersonaVulnerable persona;
  private Integer usosDia;
  private LocalDate fechaUltimoUso;
  public Boolean puedeUsar(){
    return null;
  }
}
