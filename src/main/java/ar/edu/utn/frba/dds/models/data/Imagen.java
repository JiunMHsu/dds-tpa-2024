package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Imagen {
    private String path;

    public Imagen(String path) {
        this.path = path;
    }
}
