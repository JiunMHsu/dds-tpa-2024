package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Calle {
    @Column(name = "calle_nombre")
    private String nombre;

    public Calle(String nombre) {
        this.nombre = nombre;
    }

    public Calle() {
    }
}
