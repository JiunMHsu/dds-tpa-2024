package ar.edu.utn.frba.dds.models.incidente;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.data.Imagen;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

//    public FallaTecnica(Heladera heladera,
//                        LocalDateTime fechaHora,
//                        TipoIncidente tipoIncidente,
//                        Colaborador colaborador,
//                        String descripcion,
//                        Imagen foto) {
//
//        super(heladera, fechaHora, tipoIncidente);
//        this.colaborador = colaborador;
//        this.descripcion = descripcion;
//        this.foto = foto;
//    }
//
//    public FallaTecnica() {
//        super();
//    }

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
