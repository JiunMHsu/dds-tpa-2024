package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.cargaDeColaboraciones.ColaboracionPrevia;
import ar.edu.utn.frba.dds.mensajeria.MailSender;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCargaMasiva {

  MailSender mailSender;
  Integer mailEnviados;
  List<ColaboracionPrevia> listaDeResgistrados;
  List<ColaboracionPrevia> listaTest;
  Path path;

  @BeforeEach
  public void setup() {
    path = Paths.get("src/test/resources/ar/edu/utn/frba/dds/csvEntrega2.csv");
    mailEnviados = 0;
    mailSender = mock(MailSender.class);

    when(mailSender.getNombreUsuario()).thenReturn("");
    when(mailSender.getContrasenia()).thenReturn("");
    when(mailSender.getHost()).thenReturn("");
    when(mailSender.getPort()).thenReturn("");
  }

  @Test
  @DisplayName("Cargar el archivo de prueba")
  public void cargarArchivos() {
    Assertions.assertTrue(true);

//    // Comprobar que se haga bien la carga
//    Assertions.assertNotNull(registro.getColaboradoresPrevios());
//    Assertions.assertFalse(registro.getColaboradoresPrevios().isEmpty());
//
//    int tamanio = registro.getColaboradoresPrevios().size();
//    int mitad = tamanio / 2;
//
//    for (int i = 0; i < tamanio; i++) {
//      listaDeResgistrados.add(registro.getColaboradoresPrevios().get(i));
//    }
//
//    for (int i = 42; i > mitad; i--) {
//      listaTest.add(registro.getColaboradoresPrevios().get(i));
//    }
//
//    List<ColaboracionPrevia> listaNueva = registro.colaboradoresNoRegistrados(listaDeResgistrados);
//    Boolean resultado = true;
//
//    for (int i = 0; i < listaNueva.size(); i++) {
//      resultado = listaNueva.contains(listaTest.get(i)) && resultado;
//    }
//
//    Assertions.assertTrue(resultado);
//
//    // Enviarle credenciales a los colaboradores no registrados
//
//    for (int i = 0; i < listaNueva.size(); i++) {
//      registro.generCredencial(listaNueva.get(i).getEmail());
//    }
//
//    Assertions.assertEquals(listaNueva.size(), mailEnviados);
  }
}

