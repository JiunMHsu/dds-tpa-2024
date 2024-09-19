package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Imagen {

    @Column(name = "ruta", columnDefinition = "TEXT")
    private String ruta;

    public Imagen(String ruta) {
        this.ruta = ruta;
    }

    public Imagen() {
        this.ruta = null;
    }
}
