package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.usuario.PersonaHumana;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

/**
 * 
 */
public class DonacionDineroHumana {

    /**
     * Default constructor
     */
    public DonacionDineroHumana() {
    }

    /**
     * 
     */
    private LocalDateTime fechaDonacion;

    /**
     * 
     */
    private Integer monto;

    /**
     * 
     */
    private Period frecuencia;

    /**
     * 
     */
    private PersonaHumana colaborador;

}