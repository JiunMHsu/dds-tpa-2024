package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.usuario.InfoHumana;
import ar.edu.utn.frba.dds.models.vianda.Vianda;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 
 */
public class DistribucionViandas {

    /**
     * Default constructor
     */
    public DistribucionViandas() {
    }

    /**
     * 
     */
    private Heladera origen;

    /**
     * 
     */
    private Heladera destino;

    /**
     * 
     */
    private List<Vianda> viandasAMover;

    /**
     * 
     */
    private String motivo;

    /**
     * 
     */
    private LocalDateTime fechaDistribucion;

    /**
     * 
     */
    private InfoHumana colaboradorHumano;

}