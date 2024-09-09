package ar.edu.utn.frba.dds.mensajeria;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;

import javax.persistence.*;

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

  @Column (name = "asunto")
  private String asunto;

  @Column (name = "cuerpo")
  private String cuerpo;

  @Column (name = "receptor")
  private String receptor; // Ver el tema de receptor, osea cmo es dado que seria diferente para email, wpp o telegram si no me equivoco UwU

  @Setter
  @Enumerated
  @Column (name = "medio_notificacion")
  private MedioDeNotificacion medio;

  @Setter
  @Column (name = "fecha_envio")
  private LocalDateTime fechaEnvio;

  public static Mensaje con(String asunto,
                            String cuerpo,
                            String receptor,
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
                            String receptor) {
    return Mensaje
        .builder()
        .asunto(asunto)
        .cuerpo(cuerpo)
        .receptor(receptor)
        .build();
  }
}