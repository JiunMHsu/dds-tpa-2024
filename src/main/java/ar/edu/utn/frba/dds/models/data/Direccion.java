package ar.edu.utn.frba.dds.models.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Direccion {

    @Embedded
    private Barrio barrio;

    @Embedded
    private Calle calle;

    @Column(name = "altura", nullable = false)
    private Integer altura;

    @Embedded
    private Ubicacion ubicacion;

    public static Direccion with(Barrio barrio, Calle calle, Integer altura, Ubicacion ubicacion) {
        return Direccion
                .builder()
                .barrio(barrio)
                .calle(calle)
                .altura(altura)
                .ubicacion(ubicacion)
                .build();
    }

    public static Direccion with(Calle calle, Integer altura) {
        return Direccion
                .builder()
                .calle(calle)
                .altura(altura)
                .build();
    }
}
