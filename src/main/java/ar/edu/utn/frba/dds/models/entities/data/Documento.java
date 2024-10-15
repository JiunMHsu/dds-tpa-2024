package ar.edu.utn.frba.dds.models.entities.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Documento {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipo;

    @Column(name = "numero")
    private String numero;

    public static Documento with(TipoDocumento tipoDocumento, String nroDocumento) {

        return Documento
                .builder()
                .tipo(tipoDocumento)
                .numero(nroDocumento)
                .build();
    }
}