package ar.edu.utn.frba.dds.mensajeria;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

  @Column(name = "receptor", nullable = false)
  private String receptor; // Ver el tema de receptor, osea cmo es dado que seria diferente para email, wpp o telegram si no me equivoco UwU

  @Setter
  @Enumerated
  @Column(name = "medio_notificacion", nullable = false)
  private MedioDeNotificacion medio;

  @Setter
  @Column(name = "fecha_envio", nullable = false)
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