package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Ubicacion {
    private Integer latitud;
    private Integer longitud;

    public Ubicacion(Integer latitud, Integer longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }
}