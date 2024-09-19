package ar.edu.utn.frba.dds.domain;

import static org.mockito.Mockito.mock;

import ar.edu.utn.frba.dds.cargaDeColaboraciones.CargadorDeColaboraciones;
import ar.edu.utn.frba.dds.mensajeria.EmailSender;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.usuario.Usuario;
import ar.edu.utn.frba.dds.repository.colaborador.ColaboradorRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCargaMasiva {

    CargadorDeColaboraciones cargadorDeColaboraciones;
    ColaboradorRepository colaboradorRepository;
    EmailSender mailSender;
    Path path;

    @BeforeEach
    public void setup() {
        path = Paths.get("src/test/resources/ar/edu/utn/frba/dds/csvEntrega2.csv");
        mailSender = mock(EmailSender.class);
        cargadorDeColaboraciones = new CargadorDeColaboraciones(mailSender);
        colaboradorRepository = new ColaboradorRepository();

        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("juan.perez@mail.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("maria.gomez@ejemplo.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("carlos@mail.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("ana.martinez@gmail.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("pedro.fernandez@hotmail.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("lau.garcia@outlook.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("javi.sanchez@yahoo.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("sofia.ramirez@empresa.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("martin.diaz@mail.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("cmorales@ejemplo.org")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("tomas.romero@gmail.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("flor.nunez@empresa.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("nico.herrera@hotmail.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("lcastro@outlook.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("egutierrez@yahoo.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("cami.gimenez@mail.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("matias.lopez@gmail.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("vruiz@empresa.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("santi.gomez@hotmail.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("cata@outlook.com")));
        colaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("emilio.gonzalez@yahoo.com")));
    }

    @Test
    @DisplayName("Carga de Colaboraciones Previas")
    public void cargarColaboracionesPrevias() {
        Assertions.assertEquals(21, colaboradorRepository.obtenerTodos().size());
        cargadorDeColaboraciones.cargarColaboraciones(path);
        Assertions.assertEquals(43, colaboradorRepository.obtenerTodos().size());
    }

}

