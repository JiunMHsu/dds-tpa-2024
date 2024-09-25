package ar.edu.utn.frba.dds.models.entities.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Area {

    @Embedded
    private Ubicacion ubicacion;

    @Column(name = "radio")
    private Double radio;

    @Column(name = "barrio")
    private Barrio barrio;

    public double distanciaA(Ubicacion ubicacion2) {
        double distanciaEntreUbicaciones = ubicacion.distanciaA(ubicacion2);
        return distanciaEntreUbicaciones - radio;
    }
}
