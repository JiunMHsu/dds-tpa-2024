package ar.edu.utn.frba.dds.models.heladera;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Sensor {

  private Heladera heladera;
  private String topic;

  public Sensor de(Heladera heladera, String topic) {
    return Sensor
        .builder()
        .heladera(heladera)
        .topic(topic)
        .build();
  }

  /**
   * Buider de Suscriptor de Sensor de heladeras.
   *
   * @param heladera una Heladera.
   * @return Sensor con topic vacío ("").
   */
  public Sensor de(Heladera heladera) {
    return Sensor
        .builder()
        .heladera(heladera)
        .topic("")
        .build();
  }

  /**
   * Buider de Suscriptor de Sensor de heladeras.
   *
   * @param topic una topic de suscripción.
   * @return Sensor con una heladera de capacidad 10.
   */
  public Sensor de(String topic) {
    return Sensor
        .builder()
        .heladera(Heladera.con(10))
        .topic(topic)
        .build();
  }
}
