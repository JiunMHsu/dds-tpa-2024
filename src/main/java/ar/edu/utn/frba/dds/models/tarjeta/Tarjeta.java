package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.heladera.Heladera;

public interface Tarjeta {

  Boolean puedeUsarseEn(Heladera heladera);

  String getCodigo();
}
