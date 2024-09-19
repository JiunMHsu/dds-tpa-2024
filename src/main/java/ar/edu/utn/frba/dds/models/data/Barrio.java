package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Barrio {
    @Column(name = "barrio_nombre")
    private String nombre;

    public Barrio(String nombre) {
        this.nombre = nombre;
    }

    public Barrio() {
    }
}
