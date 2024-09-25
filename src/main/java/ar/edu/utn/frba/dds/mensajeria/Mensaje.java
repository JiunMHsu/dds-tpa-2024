package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mensaje")
public class Mensaje {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(name = "asunto", nullable = false)
    private String asunto;

    @Column(name = "cuerpo", nullable = false)
    private String cuerpo;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador receptor;

    @Setter
    @Enumerated
    @Column(name = "medio_notificacion", nullable = false)
    private MedioDeNotificacion medio;

    @Setter
    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    public static Mensaje con(String asunto,
                              String cuerpo,
                              Colaborador receptor,
                              MedioDeNotificacion medio,
                              LocalDateTime fechaEnvio) {
        return Mensaje
                .builder()
                .asunto(asunto)
                .cuerpo(cuerpo)
                .receptor(receptor)
                .medio(medio)
                .fechaEnvio(fechaEnvio)
                .build();
    }

    public static Mensaje con(String asunto,
                              String cuerpo,
                              Colaborador receptor) {
        return Mensaje
                .builder()
                .asunto(asunto)
                .cuerpo(cuerpo)
                .receptor(receptor)
                .build();
    }
}