package ar.edu.utn.frba.dds.models.entities.incidente;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("falla_tecnica")
public class FallaTecnica extends Incidente {

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Embedded
    private Imagen foto;

    public static FallaTecnica de(Colaborador colaborador,
                                  String descripcion,
                                  Imagen foto) {
        return FallaTecnica
                .builder()
                .colaborador(colaborador)
                .descripcion(descripcion)
                .foto(foto)
                .build();
    }

    public static FallaTecnica de(Colaborador colaborador) {
        return FallaTecnica
                .builder()
                .colaborador(colaborador)
                .build();
    }

}
