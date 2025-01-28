package ar.edu.utn.frba.dds.models.stateless.mensajeria;

import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.mail.MessagingException;

/**
 * WhatsApp sender class.
 */
public class WhatsAppSender implements ISender {
  // TODO: leer de appProperties
  public static final String accountId = "AC4f1c322286418f58f51c78ac7f4a2b58";
  public static final String authToken = "8314490451d9bcb1d0b0f0163c244555";

  @Override
  public void enviarMensaje(Mensaje mensaje) throws IllegalArgumentException, MessagingException {

    String receptor = mensaje.getContacto().getValor();
    if (receptor == null) {
      throw new IllegalArgumentException("El contacto no tiene una cuenta de WhatsApp asociado");
    }

    Twilio.init(accountId, authToken);
    Message message = Message
        .creator(
            new PhoneNumber(receptor),
            new PhoneNumber("whatsapp:+14155238886"),
            mensaje.getAsunto() + "\n\n" + mensaje.getCuerpo()
        )
        .create();

    System.out.println("Mensaje fue enviado al WhatsApp Correctamente");
  }
}