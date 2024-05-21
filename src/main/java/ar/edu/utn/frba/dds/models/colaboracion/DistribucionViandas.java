package ar.edu.utn.frba.dds.models.colaboracion;

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
    private PersonaHumana colaboradorHumano;

}