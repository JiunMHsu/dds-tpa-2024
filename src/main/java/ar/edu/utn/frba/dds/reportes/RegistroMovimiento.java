package ar.edu.utn.frba.dds.reportes;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RegistroMovimiento {
  public static Map<String, Integer> viandasAgregadas = new HashMap();
  public static Map<String, Integer> viandasQuitadas = new HashMap();

  static {
    iniciarReinicioSemanal();
  }
  public static void agregarViandaPorHeladera(String nombreHeladera){
    viandasAgregadas.put(nombreHeladera, viandasAgregadas.getOrDefault(nombreHeladera, 0) + 1);
  }
  public static void quitarViandaPorHeladera(String nombreHeladera){
    viandasAgregadas.put(nombreHeladera, viandasQuitadas.getOrDefault(nombreHeladera, 0)  + 1);
  }
  private static void iniciarReinicioSemanal(){
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        viandasAgregadas.clear();
        viandasQuitadas.clear();
      }
    };
    timer.scheduleAtFixedRate(task,0, 604800000);
  }
}
