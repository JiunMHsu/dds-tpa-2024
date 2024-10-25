package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class RepartoDeTarjetasDTO extends ColaboracionDTO {

    private String id;

    private String colaborador;

    private String fechaHora;

    private String tarjeta;

    private String personaVulnerable;

    public static RepartoDeTarjetasDTO completa(RepartoDeTarjetas repartoDeTarjetas) {

        return RepartoDeTarjetasDTO
                .builder()
                .etiqueta(getAction(Colaboracion.REPARTO_DE_TARJETAS))
                .path(getPath(Colaboracion.REPARTO_DE_TARJETAS))
                .id(repartoDeTarjetas.getId().toString())
                .colaborador(repartoDeTarjetas.getColaborador().getUsuario().getNombre())
                .fechaHora(DateTimeParser.parseFechaHora(repartoDeTarjetas.getFechaHora()))
                .tarjeta(repartoDeTarjetas.getTarjeta().getCodigo())
                .personaVulnerable(repartoDeTarjetas.getPersonaVulnerable().getNombre())
                .build();
    }

    public static RepartoDeTarjetasDTO preview(RepartoDeTarjetas repartoDeTarjetas) { // TODO - ver si se ajusta a la vista

        return RepartoDeTarjetasDTO
                .builder()
                .etiqueta(getAction(Colaboracion.REPARTO_DE_TARJETAS))
                .path(getPath(Colaboracion.REPARTO_DE_TARJETAS))
                .id(repartoDeTarjetas.getId().toString())
                .fechaHora(DateTimeParser.parseFechaHora(repartoDeTarjetas.getFechaHora()))
                .build();
    }
}

