package ar.edu.utn.frba.dds.models.sensor;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
     * Buider de Suscriptor de Sensor de heladeras.
     *
     * @param heladera una Heladera.
     * @return Sensor con topic vacío ("").
     */
    public static Sensor de(Heladera heladera) {
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
    public static Sensor de(String topic) {
        return Sensor
                .builder()
                .heladera(Heladera.con(10))
                .topic(topic)
                .build();
    }
}
