package ar.edu.utn.frba.dds.models.usuario;

import java.util.*;

/**
 * 
 */
public class PersonaHumana {

    /**
     * Default constructor
     */
    public PersonaHumana() {
    }

    /**
     * 
     */
    private String nombre;

    /**
     * 
     */
    private String apellido;

    /**
     * 
     */
    private Contacto contacto;

    /**
     * 
     */
    private LocalDateTime fechaNacimiento;

    /**
     * 
     */
    private Direccion direccion;

    /**
     * 
     */
    private List<TipoColaboracion>?? formaDeColaborar;

    /**
     * 
     */
    private FormularioRespondido datosAdicionales;

    /**
     * 
     */
    private Usuario usuario;

}