package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class RepartoDeTarjetasDTO extends ColaboracionDTO {

    private String colaborador;

    private String tarjeta;

    private String personaVulnerable;

    public static RepartoDeTarjetasDTO completa(RepartoDeTarjetas repartoDeTarjetas) {

        return RepartoDeTarjetasDTO.builder()
                .id(repartoDeTarjetas.getId().toString())
                .nombre(TipoColaboracion.REPARTO_DE_TARJETAS.getDescription())
                .fechaHora(DateTimeParser.parseFechaHora(repartoDeTarjetas.getFechaHora()))
                .path(getPath(TipoColaboracion.REPARTO_DE_TARJETAS))
                .colaborador(repartoDeTarjetas.getColaborador().getUsuario().getNombre())
                .tarjeta(repartoDeTarjetas.getTarjeta().getCodigo())
                .personaVulnerable(repartoDeTarjetas.getPersonaVulnerable().getNombre())
                .build();
    }

    public static ColaboracionDTO preview(RepartoDeTarjetas repartoDeTarjetas) {

        return ColaboracionDTO.builder()
                .id(repartoDeTarjetas.getId().toString())
                .nombre(TipoColaboracion.REPARTO_DE_TARJETAS.getDescription())
                .fechaHora(DateTimeParser.parseFechaHora(repartoDeTarjetas.getFechaHora()))
                .path(getPath(TipoColaboracion.REPARTO_DE_TARJETAS))
                .build();
    }
}

