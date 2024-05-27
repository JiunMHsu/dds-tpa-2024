package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.heladera.Heladera;

import ar.edu.utn.frba.dds.models.usuario.Persona;
import lombok.Getter;
import lombok.Setter;

@Getter
public class HacerseCargoHeladera {

    private Heladera heladeraACargo;

    private Persona colaborador;

    public HacerseCargoHeladera(Persona colaborador, Heladera heladeraACargo) {
        this.colaborador = colaborador;
        this.heladeraACargo = heladeraACargo;
    }

}