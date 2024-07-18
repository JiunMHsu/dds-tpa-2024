package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.repository.colaboracion.DonacionViandaRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RegistroDonacion {

  public static Map<String, Integer> viandasPorColaborador = new HashMap();

  public static void actualizarViandasPorColaboradorSemanal(){
    viandasPorColaborador.clear();
    LocalDate haceUnaSemana = LocalDate.now().minus(1, ChronoUnit.WEEKS);

    List<Colaborador> colaboradores = DonacionViandaRepository.db.stream()
        .map(DonacionVianda::getColaborador)
        .distinct()
        .toList();

    for (Colaborador colaborador : colaboradores){
      List<DonacionVianda> donaciones = DonacionViandaRepository.obtenerPorColaboradorAPartirDe(colaborador, haceUnaSemana);
      int totalViandas = donaciones.size();
      viandasPorColaborador.put(colaborador.getNombre(), totalViandas);
    }
  }

  public static void iniciarActualizacionSemanal(){
    Timer timer = new Timer();
    TimerTask tareaSemanal = new TimerTask(){
      @Override
     public void run(){
        actualizarViandasPorColaboradorSemanal();
      }
    };
    timer.scheduleAtFixedRate(tareaSemanal, 0, 604800000);

  }
}
