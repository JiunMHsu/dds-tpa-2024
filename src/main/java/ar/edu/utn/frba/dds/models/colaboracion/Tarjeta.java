package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.usuario.PersonaVulnerable;
import java.time.LocalDateTime;

public class Tarjeta {
  private String codigo;
  private PersonaVulnerable persona;
  private Integer usosDia;
  private LocalDateTime fechaUltimoUso;
  public Boolean puedeUsar(){
    return null;
  }
}
