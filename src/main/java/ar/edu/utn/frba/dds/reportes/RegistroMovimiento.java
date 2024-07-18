package ar.edu.utn.frba.dds.reportes;

import java.util.HashMap;
import java.util.Map;

public class RegistroMovimiento {

  public static Map<String, Integer> viandasAgregadas = new HashMap<>();
  public static Map<String, Integer> viandasQuitadas = new HashMap<>();

  public static void agregarViandaPorHeladera(String nombreHeladera) {
    viandasAgregadas.put(nombreHeladera, viandasAgregadas.getOrDefault(nombreHeladera, 0) + 1);
  }

  public static void quitarViandaPorHeladera(String nombreHeladera) {
    viandasAgregadas.put(nombreHeladera, viandasQuitadas.getOrDefault(nombreHeladera, 0) + 1);
  }

  public static Map<String, Integer> getViandasAgregadas() {
    return new HashMap<>(viandasAgregadas);
  }

  public static Map<String, Integer> getViandasQuitadas() {
    return new HashMap<>(viandasQuitadas);
  }

  public static void vaciarHashMap() {
    viandasAgregadas.clear();
    viandasQuitadas.clear();
  }
}
