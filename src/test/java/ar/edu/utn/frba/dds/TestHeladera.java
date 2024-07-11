package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.data.Calle;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.heladera.*;
import ar.edu.utn.frba.dds.models.vianda.Vianda;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestHeladera {

  private Direccion unaDireccion;
  private RangoTemperatura rangoAEstablecer;
  private Heladera unaHeladera;

  private Vianda unaVianda;

  @BeforeEach
  public void setup() throws CapacidadExcedidaException {
    unaDireccion = new Direccion(new Calle("Medrano"), 951, null);
    rangoAEstablecer = new RangoTemperatura(5.0, -5.0);
    unaHeladera = Heladera.with("Medrano UTN", unaDireccion, 20, rangoAEstablecer);

    unaVianda = new Vianda(null, null, 9);
    unaHeladera.agregarVianda(unaVianda);
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
  public void canjeVianda() throws ViandaNoEncontradaException {
    unaHeladera.quitarVianda(unaVianda);
    Assertions.assertFalse(unaHeladera.getContenido().contains(unaVianda));
  }

  @Test
  @DisplayName("Si se intenta retirar una vianda que la heladera no tiene, falla")
  public void falloViandaInexistente() {
    Vianda viandaQueNoExisteEnHeladera = new Vianda(null, null, 12);

    try {
      unaHeladera.quitarVianda(viandaQueNoExisteEnHeladera);
      Assertions.fail("Debería fallar");
    } catch (ViandaNoEncontradaException e) {
      Assertions.assertEquals("Vianda no existe en la heladera", e.getMessage());
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
