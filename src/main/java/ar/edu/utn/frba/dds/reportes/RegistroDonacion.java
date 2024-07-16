package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.util.HashMap;
import java.util.Map;

public class RegistroDonacion {
  public static Map<String, Integer> viandasPorColaborador;

  public RegistroDonacion(){
    viandasPorColaborador = new HashMap();
  }
  public static void viandasPorColaborador(Colaborador colaborador){
    viandasPorColaborador.put(colaborador.getNombre(), viandasPorColaborador.getOrDefault(colaborador.getNombre(), 0) + 1);
  }
}
