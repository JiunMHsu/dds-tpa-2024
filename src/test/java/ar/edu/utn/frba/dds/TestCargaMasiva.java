package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.convertidorArchivos.CargadorColaboraciones;
import ar.edu.utn.frba.dds.models.convertidorArchivos.ColaboradoresPrevios;
import ar.edu.utn.frba.dds.models.convertidorArchivos.GeneradorDeCredencial;
import ar.edu.utn.frba.dds.models.convertidorArchivos.RegistroColaboradoresPrevios;
import ar.edu.utn.frba.dds.models.mailSender.MailSender;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestCargaMasiva {

    CargadorColaboraciones cargador = new CargadorColaboraciones();
    RegistroColaboradoresPrevios registro = new RegistroColaboradoresPrevios();
    MailSender mailSender = new MailSender(null, null, null, null);

    @Test
    @DisplayName("Cargar el archivo de prueba")

    public void comprobarCargaMasiva() {

        Path path = Paths.get("src/test/resources/ar/edu/utn/frba/dds/csvEntrega2.csv");

        registro.setColaboradoresPrevios(cargador.convertirALista(path));

        // Comprobar que se haga bien la carga

        Assertions.assertNotNull(registro.getColaboradoresPrevios());
        Assertions.assertFalse(registro.getColaboradoresPrevios().isEmpty());

        int tamanio = registro.getColaboradoresPrevios().size();
        int mitad = tamanio / 2 + (tamanio % 2);

        for (int i = 0; i < mitad; i++) {
            registro.getColaboradoresPrevios().get(i).setRegistrado(true);
        }

        // Comprobar que la mitad no este registrada

        Assertions.assertEquals(mitad, registro.colaboradoresNoRegistrados().size());



    }
}
