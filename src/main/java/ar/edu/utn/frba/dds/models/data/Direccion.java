package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Direccion {

    private String calle;
    private Integer altura;
    private Ubicacion ubicacion;


    public Direccion(String calle, Integer altura, Ubicacion ubicacion) {
        this.calle = calle;
        this.altura = altura;
        this.ubicacion = ubicacion;
    }
}
