package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import lombok.Getter;

@Getter
public class RegistroIncidente {
  
  public static Map<String, Integer> incidentesPorHeladera = new HashMap();
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
