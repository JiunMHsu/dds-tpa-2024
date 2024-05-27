package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.usuario.InfoHumana;
import ar.edu.utn.frba.dds.models.usuario.Persona;

import java.time.LocalDateTime;
import java.time.Period;

/**
 * 
 */
public class DonacionDinero {

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
    private Persona colaborador;

    public DonacionDinero() {
    }

    public Integer getMonto() {
        return monto;
    }
}