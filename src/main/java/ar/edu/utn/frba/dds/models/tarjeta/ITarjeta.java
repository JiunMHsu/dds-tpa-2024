package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.heladera.Heladera;

public interface ITarjeta {

  void registrarUso(Heladera heladera) throws ExcepcionUsoInvalido;

  String getCodigo();
}
