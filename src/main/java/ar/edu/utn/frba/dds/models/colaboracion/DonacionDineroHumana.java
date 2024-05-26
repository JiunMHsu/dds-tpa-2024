package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.usuario.InfoHumana;

import java.time.LocalDateTime;
import java.time.Period;

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
    private InfoHumana colaborador;

}