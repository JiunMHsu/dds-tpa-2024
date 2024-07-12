package ar.edu.utn.frba.dds.models.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Mail {

  private String destinatario;
  private String asunto;
  private String cuerpo;

  public static Mail to(String destinatario, String asunto, String cuerpo) {
    return Mail
        .builder()
        .destinatario(destinatario)
        .asunto(asunto)
        .cuerpo(cuerpo)
        .build();
  }

  public static Mail to(String destinatario) {
    return Mail
        .builder()
        .destinatario(destinatario)
        .asunto("")
        .cuerpo("")
        .build();
  }

  public static Mail to() {
    return Mail
        .builder()
        .build();
  }
}
