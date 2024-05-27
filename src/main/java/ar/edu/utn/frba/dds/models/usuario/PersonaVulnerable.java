package ar.edu.utn.frba.dds.models.usuario;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.Documento;

import java.time.LocalDate;

/**
 * 
 */
public class PersonaVulnerable {

    /**
     * Default constructor
     */
    public PersonaVulnerable() {
    }

    /**
     * 
     */
    private String nombre;

    /**
     * 
     */
    private LocalDate fechaNacimiento;

    /**
     * 
     */
    private LocalDate fechaRegistro;

    /**
     * 
     */
    private Direccion domicilio;

    /**
     * 
     */
    private Documento documento;

    /**
     * 
     */
    private Integer menoresACargo;

}