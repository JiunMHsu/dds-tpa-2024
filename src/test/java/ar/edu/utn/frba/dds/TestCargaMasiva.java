package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.convertidorArchivos.*;

import ar.edu.utn.frba.dds.utils.GeneradorDeCredencial;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.senders.MailSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class TestCargaMasiva {

  RegistroColaboradoresPrevios registro;
  GeneradorDeCredencial generador;
  MailSender mailSender;
  Integer mailEnviados;
  List<ColaboradoresPrevios> listaDeResgistrados;
  List<ColaboradoresPrevios> listaTest;

  @BeforeEach
  public void setup() {

    registro = new RegistroColaboradoresPrevios();

    generador = new GeneradorDeCredencial();

    listaDeResgistrados = new ArrayList<>();
    listaTest = new ArrayList<>();

    Path path = Paths.get("src/test/resources/ar/edu/utn/frba/dds/csvEntrega2.csv");
    registro.cargarColaboraciones(path);

    mailEnviados = 0;

    mailSender = mock(MailSender.class);

    doAnswer(invocation -> {
      mailEnviados++;
      return null;
    }).when(mailSender).enviarMail(anyString(), anyString(), anyString());
  }

  @Test
  @DisplayName("Cargar el archivo de prueba")
  public void cargarArchivos() {

    // Comprobar que se haga bien la carga
    Assertions.assertNotNull(registro.getColaboradoresPrevios());
    Assertions.assertFalse(registro.getColaboradoresPrevios().isEmpty());

    int tamanio = registro.getColaboradoresPrevios().size();
    int mitad = tamanio / 2;

    for (int i = 0; i < tamanio; i++) {
      listaDeResgistrados.add(registro.getColaboradoresPrevios().get(i));
    }

    for (int i = 42; i > mitad; i--) {
      listaTest.add(registro.getColaboradoresPrevios().get(i));
    }

    List<ColaboradoresPrevios> listaNueva = registro.colaboradoresNoRegistrados(listaDeResgistrados);
    Boolean resultado = true;

    for (int i = 0; i < listaNueva.size(); i++) {
      resultado = listaNueva.contains(listaTest.get(i)) && resultado;
    }

    Assertions.assertTrue(resultado);

    // Enviarle credenciales a los colaboradores no registrados

    for (int i = 0; i < listaNueva.size(); i++) {
      registro.generCredencial(listaNueva.get(i).getEmail());
    }

    Assertions.assertEquals(listaNueva.size(), mailEnviados);
  }
}

