package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.data.Imagen;
import ar.edu.utn.frba.dds.models.usuario.Persona;
import lombok.Getter;

@Getter
public class OfrecerProducto {
    private Persona colaborador;
    private String nombreOferta;
    private Integer puntosNecesarios;
    private Imagen imagen;

    public OfrecerProducto(Persona colaborador, String nombreOferta, Integer puntosNecesarios, Imagen imagen) {
        this.colaborador = colaborador;
        this.nombreOferta = nombreOferta;
        this.puntosNecesarios = puntosNecesarios;
        this.imagen = imagen;
    }
}
