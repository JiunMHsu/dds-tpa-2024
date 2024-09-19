package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

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