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

    public PersonaVulnerable() {
        this.nombre = null;
        this.fechaNacimiento = null;
        this.fechaRegistro = null;
        this.domicilio = null;
        this.documento = null;
        this.menoresACargo = null;
    }

}