package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.usuario.PersonaHumana;
import ar.edu.utn.frba.dds.models.vianda.Vianda;

import java.time.LocalDateTime;
import java.util.*;

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
    private PersonaHumana colaboradorHumano;

}