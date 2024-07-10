package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.tarjeta.Tarjeta;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.personaVulnerable.PersonaVulnerable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepartoDeTarjetas {

  private Colaborador colaborador;
  private Tarjeta tarjeta;
  private PersonaVulnerable personaVulnerable;

  public RepartoDeTarjetas(Colaborador colaborador, Tarjeta tarjeta, PersonaVulnerable personaVulnerable) {

    this.colaborador = colaborador;
    this.tarjeta = tarjeta;
    this.personaVulnerable = personaVulnerable;
  }
}
