package ar.edu.utn.frba.dds.reportes;

import java.util.HashMap;
import java.util.Map;

public class RegistroMovimiento {
  private static Map<String, Integer> viandasAgregadas;
  private static Map<String, Integer> viandasQuitadas;

  public RegistroMovimiento(){
    viandasAgregadas = new HashMap();
    viandasQuitadas = new HashMap();
  }
  public static void agregarViandaPorHeladera(String nombreHeladera){
    viandasAgregadas.put(nombreHeladera, viandasAgregadas.getOrDefault(nombreHeladera, 0) + 1);
  }
  public static void quitarViandaPorHeladera(String nombreHeladera){
    viandasAgregadas.put(nombreHeladera, viandasQuitadas.getOrDefault(nombreHeladera, 0)  + 1);
  }
}
