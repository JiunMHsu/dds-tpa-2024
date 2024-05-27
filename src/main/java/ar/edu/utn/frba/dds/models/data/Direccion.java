package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Direccion {

    private String calle;
    private Integer altura;
    private Ubicacion ubicacion;

    public Direccion(String Cclle, Integer altura, Ubicacion ubicacion) {
        this.calle = Calle;
        this.altura = altura;
        this.ubicacion = ubicacion;
    }
}
