package ar.edu.utn.frba.dds.reportes;

import java.util.HashMap;
import java.util.Map;

public class RegistroMovimiento {
  public static final Map<String, Integer> viandasAgregadas = new HashMap();
  public static final Map<String, Integer> viandasQuitadas = new HashMap();

  public static void agregarViandaPorHeladera(String nombreHeladera) {
    viandasAgregadas.put(nombreHeladera, viandasAgregadas.getOrDefault(nombreHeladera, 0) + 1);
  }

  public static void quitarViandaPorHeladera(String nombreHeladera) {
    viandasAgregadas.put(nombreHeladera, viandasQuitadas.getOrDefault(nombreHeladera, 0) + 1);
  }
}
