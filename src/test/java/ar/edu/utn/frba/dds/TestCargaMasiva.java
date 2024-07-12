package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.mensajeria.MailSender;

import ar.edu.utn.frba.dds.models.data.Mail;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class TestCargaMasiva {

  MailSender mailSender;
  Integer mailEnviados;
  Path path;

  @BeforeEach
  public void setup() {
    path = Paths.get("src/test/resources/ar/edu/utn/frba/dds/csvEntrega2.csv");
    mailEnviados = 0;
    mailSender = mock(MailSender.class);

    doAnswer(invocation -> {
      mailEnviados++;
      return null;
    }).when(mailSender).enviarMail(Mail.to());

    // hacer setup del repositorio
  }

  @Test
  @DisplayName("Carga de Colaboradores Existentes")
  public void cargarColaboradoresExistentes() {
    Assertions.assertTrue(true);
  }

  @Test
  @DisplayName("Carga de Colaboradores Nuevos")
  public void cargarColaboradoresNuevos() {
    Assertions.assertTrue(true);
  }
}

