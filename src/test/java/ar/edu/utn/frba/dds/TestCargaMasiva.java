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

    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "juan.perez@mail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "maria.gomez@ejemplo.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "carlos@mail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "ana.martinez@gmail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "pedro.fernandez@hotmail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "lau.garcia@outlook.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "javi.sanchez@yahoo.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "sofia.ramirez@empresa.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "martin.diaz@mail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "cmorales@ejemplo.org")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "tomas.romero@gmail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "flor.nunez@empresa.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "nico.herrera@hotmail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "lcastro@outlook.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "egutierrez@yahoo.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "cami.gimenez@mail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "matias.lopez@gmail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "vruiz@empresa.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "santi.gomez@hotmail.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "cata@outlook.com")));
    ColaboradorRepository.agregar(Colaborador.colaborador(new Usuario("", "", "emilio.gonzalez@yahoo.com")));

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

