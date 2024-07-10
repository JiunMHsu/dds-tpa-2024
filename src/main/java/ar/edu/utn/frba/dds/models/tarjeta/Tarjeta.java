package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.heladera.Heladera;

public interface Tarjeta {
  Boolean puedeUsar(Heladera heladera);

  void registrarUso(Heladera heladera);
}
