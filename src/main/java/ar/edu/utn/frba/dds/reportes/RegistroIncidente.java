package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.incidente.Incidente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import lombok.Getter;

@Getter
public class RegistroIncidente {
  public static Map<String, Integer> incidentesPorHeladera = new HashMap();
  static {
    iniciarReinicioSemanal();
  }
  public static void incidentePorHeladeras(Heladera heladera){
    incidentesPorHeladera.put(heladera.getNombre(), incidentesPorHeladera.getOrDefault(heladera.getNombre(), 0) + 1);
  }
  private static void iniciarReinicioSemanal(){
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        incidentesPorHeladera.clear();
      }
    };
    timer.scheduleAtFixedRate(task,0, 604800000);
  }
}
