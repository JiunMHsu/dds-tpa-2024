package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.tarjeta.Tarjeta;
import ar.edu.utn.frba.dds.models.usuario.Persona;
import ar.edu.utn.frba.dds.models.usuario.PersonaVulnerable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepartoDeTarjetas {

    private Persona colaborador;
    private Tarjeta tarjeta;
    private PersonaVulnerable personaVulnerable;

    public RepartoDeTarjetas(Persona colaborador, Tarjeta tarjeta, PersonaVulnerable personaVulnerable) {

        this.colaborador = colaborador;
        this.tarjeta = tarjeta;
        this.personaVulnerable = personaVulnerable;
    }
}
