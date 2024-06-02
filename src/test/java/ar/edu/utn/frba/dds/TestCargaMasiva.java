package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.convertidorArchivos.CargadorColaboraciones;
import ar.edu.utn.frba.dds.models.convertidorArchivos.ColaboradoresPrevios;
import ar.edu.utn.frba.dds.models.convertidorArchivos.RegistroColaboradoresPrevios;
import ar.edu.utn.frba.dds.models.mailSender.MailSender;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

        List<ColaboradoresPrevios> listaDeResgistrados = new ArrayList<>();
        List<ColaboradoresPrevios> listaTest = new ArrayList<>();

        Path path = Paths.get("src/test/resources/ar/edu/utn/frba/dds/csvEntrega2.csv");
        registro.cargarColaboraciones(path);

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

        for (int i = 0; i < listaNueva.size(); i++) {
            Assertions.assertTrue(listaNueva.contains(listaTest.get(i)));
        }
    }
}
