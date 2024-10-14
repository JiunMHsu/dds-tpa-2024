package ar.edu.utn.frba.dds.dtos.suscripcion;

import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FallaHeladeraDTO {

    private String colaborador;

    private String heladera;

    private String medioDeNotificacion;

    public static FallaHeladeraDTO completa(SuscripcionFallaHeladera suscripcionFallaHeladera) {

        return FallaHeladeraDTO
                .builder()
                .colaborador(suscripcionFallaHeladera.getColaborador().getUsuario().getNombre())
                .heladera(suscripcionFallaHeladera.getHeladera().getNombre())
                .medioDeNotificacion(suscripcionFallaHeladera.getMedioDeNotificacion().toString())
                .build();
    }
}
