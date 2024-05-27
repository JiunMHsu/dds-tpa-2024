package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.usuario.InfoHumana;
import ar.edu.utn.frba.dds.models.vianda.Vianda;

import java.time.LocalDateTime;

/**
 * 
 */
public class DonacionVianda {

    /**
     * Default constructor
     */
    public DonacionVianda() {
    }

    /**
     * 
     */
    private Vianda vianda;

    /**
     * 
     */
    private LocalDateTime fechaDonacion;

    /**
     * 
     */
    private Heladera heladera;

    /**
     * 
     */
    private InfoHumana colaboradorHumano;

}