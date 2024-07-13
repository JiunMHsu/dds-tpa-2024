package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.util.HashMap;
import java.util.Map;

public class RegistroDonacion {
  private static Map<Colaborador, Integer> viandasPorColaborador;

  public RegistroDonacion(){
    viandasPorColaborador = new HashMap();
  }
  public static void viandasPorColaborador(Colaborador colaborador){
    viandasPorColaborador.put(colaborador, viandasPorColaborador.getOrDefault(colaborador, 0) + 1);
  }
}
