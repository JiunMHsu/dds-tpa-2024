package ar.edu.utn.frba.dds.reportes;

import java.util.HashMap;
import java.util.Map;

public class RegistroMovimiento {

    private static RegistroMovimiento instancia;
    private final Map<String, Integer> viandasAgregadas;
    private final Map<String, Integer> viandasQuitadas;

    private RegistroMovimiento() {
        viandasAgregadas = new HashMap<>();
        viandasQuitadas = new HashMap<>();
    }

    public static RegistroMovimiento getInstancia() {
        if (instancia == null) {
            instancia = new RegistroMovimiento();
        }

        return instancia;
    }

    public Map<String, Integer> getViandasAgregadas() {
        return new HashMap<>(viandasAgregadas);
    }

    public Map<String, Integer> getViandasQuitadas() {
        return new HashMap<>(viandasQuitadas);
    }

    public void vaciarRegistro() {
        viandasAgregadas.clear();
        viandasQuitadas.clear();
    }

    public void quitarViandaPara(String nombreHeladera) {
        viandasAgregadas.put(nombreHeladera, viandasQuitadas.getOrDefault(nombreHeladera, 0) + 1);
    }

    public void sumarViandaPara(String nombreHeladera) {
        viandasAgregadas.put(nombreHeladera, viandasAgregadas.getOrDefault(nombreHeladera, 0) + 1);
    }

}
