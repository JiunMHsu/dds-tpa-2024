package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.mailSender.MailSender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestMailSender {

    MailSender mailSender;
    String destinatario;
    String asunto;
    String cuerpo;
    Integer mailEnviados;

    @BeforeEach
    public void setup() {

        destinatario = "leoneljuncossmieres@gmail.com";

        asunto = "Prueba Test - 2da Entrega";

        cuerpo = "Buenos dias estimado Ming" +
                 "Vamos a realizar una pruba del mail sender." +
                 "La idea es mandar un mail y comprobar que efectivaente se envio" +
                 "Gracias de antemano";

        mailEnviados = 0;

        mailSender = mock(MailSender.class);

        doAnswer(invocation -> {
            mailEnviados++;
            return null;
        }).when(mailSender).enviarMail(anyString(), anyString(), anyString());

    }

    @Test
    @DisplayName("Se envia un mail.")
    public void enviarUnMail() {
        mailSender.enviarMail(destinatario, asunto, cuerpo);
        Assertions.assertTrue(mailEnviados == 1);
    }
}
