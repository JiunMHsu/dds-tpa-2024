package ar.edu.utn.frba.dds.models.heladera;

public class SensorTemperatura {

  public SensorTemperatura() {}

  public void recibirTemperatura(Double temperatura, Heladera heladera) {
    heladera.verificarTemperatura(temperatura);
  }
}
