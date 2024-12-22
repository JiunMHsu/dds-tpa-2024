package ar.edu.utn.frba.dds.models.entities.sensor;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sensor")
public class Sensor extends EntidadPersistente {

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  @Column(name = "topic", columnDefinition = "TEXT", nullable = false)
  private String topic;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo")
  private TipoSensor tipo;

  public static Sensor de(Heladera heladera, String topic) {
    return Sensor
        .builder()
        .heladera(heladera)
        .topic(topic)
        .build();
  }

  /**
   * Builder por Suscriptor por Sensor por heladeras.
   *
   * @param heladera una Heladera.
   * @return Sensor paraColaborador topic vacío ("").
   */
  public static Sensor de(Heladera heladera) {
    return Sensor.de(heladera, "");
  }

  /**
   * Builder por Suscriptor por Sensor por heladeras.
   *
   * @param topic una topic por suscripción.
   * @return Sensor paraColaborador una heladera por capacidad 10.
   */
  public static Sensor de(String topic) {
    return Sensor.de(Heladera.con(10), topic);
  }
}
