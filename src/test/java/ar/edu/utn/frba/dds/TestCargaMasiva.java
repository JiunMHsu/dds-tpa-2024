package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.cargaDeColaboraciones.CargadorDeColaboraciones;
import ar.edu.utn.frba.dds.mensajeria.MailSender;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.colaborador.Usuario;
import ar.edu.utn.frba.dds.repository.colaborador.ColaboradorRepository;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class TestCargaMasiva {

  CargadorDeColaboraciones cargadorDeColaboraciones;
  MailSender mailSender;
  Path path;

  @BeforeEach
  public void setup() {
    path = Paths.get("src/test/resources/ar/edu/utn/frba/dds/csvEntrega2.csv");
    mailSender = mock(MailSender.class);
    cargadorDeColaboraciones = new CargadorDeColaboraciones(mailSender);

    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("juan.perez@mail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("maria.gomez@ejemplo.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("carlos@mail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("ana.martinez@gmail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("pedro.fernandez@hotmail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("lau.garcia@outlook.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("javi.sanchez@yahoo.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("sofia.ramirez@empresa.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("martin.diaz@mail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("cmorales@ejemplo.org")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("tomas.romero@gmail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("flor.nunez@empresa.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("nico.herrera@hotmail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("lcastro@outlook.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("egutierrez@yahoo.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("cami.gimenez@mail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("matias.lopez@gmail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("vruiz@empresa.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("santi.gomez@hotmail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("cata@outlook.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(Usuario.withEmail("emilio.gonzalez@yahoo.com")));

    /**
     * Tipo Doc,Documento,Nombre,Apellido,Mail,Fecha de colaboración,Forma de colaboración,Cantidad
     * DNI,36985214,Juan,Pérez,juan.perez@mail.com,18/05/2024,DINERO,5000
     * ...
     * LE,57418269,Emilio,González,emilio.gonzalez@yahoo.com,26/05/2024,DINERO,4500
     */
  }

  @Test
  @DisplayName("Carga de Colaboraciones Previas")
  public void cargarColaboracionesPrevias() {
    Assertions.assertEquals(21, ColaboradorRepository.obtenerTodos().size());
    cargadorDeColaboraciones.cargarColaboraciones(path);
    Assertions.assertEquals(43, ColaboradorRepository.obtenerTodos().size());
  }

}

