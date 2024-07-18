package ar.edu.utn.frba.dds.models.data;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class Mensaje {

  private String body;
  private LocalDateTime fechaEnvio;
  private Contacto destinatario;
  private MedioDeNotificacion medio;

  public static Mensaje create(String body,
                               LocalDateTime fechaEnvio,
                               Contacto destinatario,
                               MedioDeNotificacion medio) {
    return Mensaje
        .builder()
        .body(body)
        .fechaEnvio(fechaEnvio)
        .destinatario(destinatario)
        .medio(medio)
        .build();
  }
}