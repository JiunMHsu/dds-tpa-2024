package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.vianda.Vianda;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 
 */
public class Heladera {

    /**
     * Default constructor
     */
    public Heladera() {
    }

    /**
     * 
     */
    private String nombre;

    /**
     * 
     */
    private Direccion direccion;

    /**
     * 
     */
    private LocalDateTime fechaInicioFuncionamiento;

    /**
     * 
     */
    private Integer capacidad;

    /**
     * 
     */
    private List<Vianda> contenido;

    /**
     * @param vianda 
     * @return
     */
    public void agregarVianda(Vianda vianda) {
        // TODO implement here
        return null;
    }

    /**
     * @param vianda 
     * @return
     */
    public void quitarVianda(Vianda vianda) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    private Boolean chequearCapacidad() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    private Integer cantDeViandas() {
        // TODO implement here
        return null;
    }

}