package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.data.Calle;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.heladera.ExcepcionCantidadDeViandas;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.heladera.RangoTemperatura;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestHeladera {

  private Direccion unaDireccion;
  private RangoTemperatura rangoAEstablecer;
  private Heladera unaHeladera;

  @BeforeEach
  public void setup() throws ExcepcionCantidadDeViandas {
    unaDireccion = Direccion.with(new Calle("Medrano"), 951);
    rangoAEstablecer = new RangoTemperatura(5.0, -5.0);
    unaHeladera = Heladera.con("Medrano UTN", unaDireccion, 2, rangoAEstablecer);
    unaHeladera.setEstado(EstadoHeladera.ACTIVA);

    unaHeladera.agregarViandas(1);
  }

  @Test
  @DisplayName("Se permite dar de alta una heladera")
  public void crearHeladera() {
    Assertions.assertEquals("Medrano UTN", unaHeladera.getNombre());
    Assertions.assertEquals(unaDireccion, unaHeladera.getDireccion());
    Assertions.assertEquals(rangoAEstablecer, unaHeladera.getRangoTemperatura());
  }

  @Test
  @DisplayName("Se puede retirar una vianda de la heladera")
  public void canjeVianda() {
    try {
      unaHeladera.quitarViandas(1);
      Assertions.assertEquals(0, unaHeladera.getViandas(),
          "Al retirar la única vianda que quedaba de la Heladera, no quedan más viandas.");
    } catch (ExcepcionCantidadDeViandas e) {
      Assertions.fail("No se pudo quitar vianda.");
    }
  }

  @Test
  @DisplayName("Se puede agregar una vianda a la heladera")
  public void agregarVianda() {
    Assertions.assertEquals(1, unaHeladera.getViandas());

    try {
      unaHeladera.agregarViandas(1);
      Assertions.assertEquals(2, unaHeladera.getViandas(),
          "Al agregar una vianda a la Heladera que ya tenía una, ahora tiene dos.");
    } catch (ExcepcionCantidadDeViandas e) {
      Assertions.fail("No se pudo agregar la vianda.");
    }
  }

  @Test
  @DisplayName("No se puede agregar más viandas si la heladera está llena")
  public void agregarViandaAHeladeraLlena() {
    Assertions.assertEquals(2, unaHeladera.getCapacidad());
    Assertions.assertEquals(1, unaHeladera.getViandas());

    try {
      unaHeladera.agregarViandas(2);
      Assertions.fail("No hubo excepción.");
    } catch (ExcepcionCantidadDeViandas e) {
      Assertions.assertNotNull(e);
    }
  }

  @Test
  @DisplayName("Se puede verificar si una temperatura es segura o no según el rango establecido")
  public void verificarTemperaturaSegura() {
    Double unaTemperatura = 9.0;
    Double otraTemperatura = 1.0;

    Assertions.assertFalse(unaHeladera.getRangoTemperatura().incluye(unaTemperatura));
    Assertions.assertTrue(unaHeladera.getRangoTemperatura().incluye(otraTemperatura));
  }

  @Test
  @DisplayName("Se puede configurar el Rango de Temperatura")
  public void configurarRangoTemperatura() {
    Double temperaturaActual = 9.0;
    RangoTemperatura nuevoRango = new RangoTemperatura(10.0, -10.0);

    Assertions.assertFalse(unaHeladera.getRangoTemperatura().incluye(temperaturaActual));
    unaHeladera.setRangoTemperatura(nuevoRango);
    Assertions.assertTrue(unaHeladera.getRangoTemperatura().incluye(temperaturaActual));
  }
}
