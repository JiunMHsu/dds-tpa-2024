package ar.edu.utn.frba.dds.models.entities.mensajeria;

import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.mail.MessagingException;

public class WhatsAppSender implements ISender {
  public static final String ACCOUNT_SID = "AC4f1c322286418f58f51c78ac7f4a2b58";
  public static final String AUTH_TOKEN = "8314490451d9bcb1d0b0f0163c244555";

  @Override
  public void enviarMensaje(Contacto contacto, String asunto, String cuerpo) throws IllegalArgumentException, MessagingException {

    String receptor = contacto.getContacto(MedioDeNotificacion.WHATSAPP);
    if (receptor == null)
      throw new IllegalArgumentException("El contacto no tiene una cuenta de WhatsApp asociado");

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message
        .creator(
            new PhoneNumber(receptor),
            new PhoneNumber("whatsapp:+14155238886"),
            asunto + "\n\n" + cuerpo
        )
        .create();

    System.out.println("Mensaje fue enviado al WhatsApp Correctamente");
  }
}