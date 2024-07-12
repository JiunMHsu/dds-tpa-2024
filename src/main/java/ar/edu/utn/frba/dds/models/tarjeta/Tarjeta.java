package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.heladera.Heladera;

public interface Tarjeta {
  void registrarUso(Heladera heladera) throws ExcepcionUsoInvalido;
}
