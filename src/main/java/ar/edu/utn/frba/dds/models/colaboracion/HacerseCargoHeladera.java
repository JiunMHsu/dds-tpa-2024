package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.heladera.Heladera;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import lombok.Getter;

@Getter
public class HacerseCargoHeladera {

  private Heladera heladeraACargo;

  private Colaborador colaborador;

  public HacerseCargoHeladera(Colaborador colaborador, Heladera heladeraACargo) {
    this.colaborador = colaborador;
    this.heladeraACargo = heladeraACargo;
  }

}