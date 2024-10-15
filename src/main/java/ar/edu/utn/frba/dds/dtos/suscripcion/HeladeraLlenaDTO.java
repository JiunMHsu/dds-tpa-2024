package ar.edu.utn.frba.dds.dtos.suscripcion;

import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionHeladeraLlena;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HeladeraLlenaDTO {

    private String colaborador;

    private String heladera;

    private String medioDeNotificacion;

    private String espacioRestante;

    public static HeladeraLlenaDTO completa(SuscripcionHeladeraLlena suscripcionHeladeraLlena) {

        return HeladeraLlenaDTO
                .builder()
                .colaborador(suscripcionHeladeraLlena.getColaborador().getUsuario().getNombre())
                .heladera(suscripcionHeladeraLlena.getHeladera().getNombre())
                .medioDeNotificacion(suscripcionHeladeraLlena.getMedioDeNotificacion().toString())
                .espacioRestante(suscripcionHeladeraLlena.getEspacioRestante().toString())
                .build();
    }
}
