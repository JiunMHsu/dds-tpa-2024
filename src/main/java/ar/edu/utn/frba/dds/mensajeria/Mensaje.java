package ar.edu.utn.frba.dds.mensajeria;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class Mensaje {

  private String asunto;
  private String cuerpo;
  private String receptor;

  @Setter
  private MedioDeNotificacion medio;

  @Setter
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