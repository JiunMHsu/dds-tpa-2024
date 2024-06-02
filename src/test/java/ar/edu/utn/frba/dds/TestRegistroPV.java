package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.tarjeta.Tarjeta;
import ar.edu.utn.frba.dds.models.usuario.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.data.Ubicacion;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.Documento;
import ar.edu.utn.frba.dds.models.data.TipoDocumento;

import ar.edu.utn.frba.dds.models.usuario.Persona;
import ar.edu.utn.frba.dds.models.usuario.Usuario;
import ar.edu.utn.frba.dds.models.usuario.TipoDePersona;
import ar.edu.utn.frba.dds.models.usuario.Sujeto;
import ar.edu.utn.frba.dds.models.colaboracion.TipoColaboracion;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



public class TestRegistroPV {

    @Test
    @DisplayName("Test del registro de persona vulnerable")

    public void testResgistroPersonaVulnerable() {

        Ubicacion ubicacionTest = new Ubicacion( 48, 84);
        Direccion direccionTest = new Direccion("Aguero", 1281, ubicacionTest);
        Documento documentoTest = new Documento(12345678, TipoDocumento.DNI);

        // Personas Vulnerables

        PersonaVulnerable personaRegistrada = new PersonaVulnerable();

        personaRegistrada.setNombre("Joaquin");
        personaRegistrada.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        personaRegistrada.setFechaRegistro(LocalDate.of(2023, 5, 1));
        personaRegistrada.setDomicilio(direccionTest);
        personaRegistrada.setDocumento(documentoTest);
        personaRegistrada.setMenoresACargo(2);

        PersonaVulnerable personaNoRegistrada = new PersonaVulnerable();

        personaNoRegistrada.setNombre("Matias");
        personaNoRegistrada.setFechaNacimiento(LocalDate.of(2010, 2, 23));
        personaNoRegistrada.setFechaRegistro(LocalDate.of(2022, 4, 12));
        personaNoRegistrada.setDomicilio(direccionTest);
        personaNoRegistrada.setDocumento(documentoTest);
        personaNoRegistrada.setMenoresACargo(2);

        // Colaborador

        Usuario usuario = new Usuario("usuarioTest", "pancho", "pancho@gmail.com");

        List<TipoColaboracion> colaboraciones = Collections.singletonList(TipoColaboracion.RepartirTarjeta);
        TipoDePersona tipoDePersona = new TipoDePersona(Sujeto.Humana, colaboraciones);

        Persona personaColaborador = new Persona(usuario);
        personaColaborador.setTipoDePersona(tipoDePersona);

        // Resgistro

        RepartoDeTarjetas repartoTest = new RepartoDeTarjetas(personaColaborador, new Tarjeta(), personaRegistrada);

        Assertions.assertEquals(personaRegistrada, repartoTest.getPersonaVulnerable(),
                "La persona registrada no está correctamente registrada.");

        Assertions.assertNotEquals(personaNoRegistrada, repartoTest.getPersonaVulnerable(),
                "La persona no está registrada dado que no recibio una tarjeta.");
    }
}

