package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.heladera.RangoTemperatura;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestHeladera {

  @Test
  @DisplayName("Se permite der de alta una heladera")
  public void crearHeladera(){
    Direccion unaDireccion = new Direccion("Medrano", 951, null);
    RangoTemperatura rangoAEstablecer = new RangoTemperatura(5.0, -5.0);
    Heladera heladera = new Heladera("Medrano UTN", unaDireccion, 20, rangoAEstablecer);

    Assertions.assertEquals("Medrano UTN", heladera.getNombre());
    Assertions.assertEquals(unaDireccion, heladera.getDireccion());
    Assertions.assertEquals(rangoAEstablecer, heladera.getRangoTemperatura());
    Assertions.assertTrue(heladera.estaActiva());
  }

  @Test
  @DisplayName("Se puede retirar una vianda de la heladera")
  public void test(){

  }
  // se puede quitar vianda
  // se puede setear el rango de temperatura
  // se puede verificar la temperatura
}
