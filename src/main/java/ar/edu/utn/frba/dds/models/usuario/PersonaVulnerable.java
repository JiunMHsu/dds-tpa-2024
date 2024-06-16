package ar.edu.utn.frba.dds.models.usuario;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.Documento;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PersonaVulnerable {
    private String nombre;
    private LocalDate fechaNacimiento;
    private LocalDate fechaRegistro;
    private Direccion domicilio;
    private Documento documento;
    private Integer menoresACargo;

    public static PersonaVulnerable with(String nombre, Direccion domicilio, Documento documento, Integer menoresACargo){
        return PersonaVulnerable
            .builder()
            .nombre(nombre)
            .domicilio(domicilio)
            .documento(documento)
            .menoresACargo(menoresACargo)
            .build();
    }
    public static PersonaVulnerable with(String nombre, Direccion domicilio, Documento documento){
        return PersonaVulnerable
            .builder()
            .nombre(nombre)
            .domicilio(domicilio)
            .documento(documento)
            .build();
    }
    public static PersonaVulnerable with(String nombre, Direccion domicilio){
        return PersonaVulnerable
            .builder()
            .nombre(nombre)
            .domicilio(domicilio)
            .build();
    }
    public static PersonaVulnerable with(String nombre){
        return PersonaVulnerable
            .builder()
            .nombre(nombre)
            .build();
    }
    public static PersonaVulnerable with( Integer menoresACargo){
        return PersonaVulnerable
            .builder()
            .menoresACargo(menoresACargo)
            .build();
    }

}

