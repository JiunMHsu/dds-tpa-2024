package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Mail {

  private String destinatario;
  private String asunto;
  private String cuerpo;

  public Mail(String destinatario, String asunto, String cuerpo) {
    this.destinatario = destinatario;
    this.asunto = asunto;
    this.cuerpo = cuerpo;
  }
}
