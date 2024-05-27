package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.vianda.Vianda;

import java.time.LocalDate;

public class DonacionVianda {

    private Vianda vianda;
    private LocalDate fechaDonacion;
    private Heladera heladera;
    private InfoHumana colaboradorHumano;
    public DonacionVianda() {
    }

}