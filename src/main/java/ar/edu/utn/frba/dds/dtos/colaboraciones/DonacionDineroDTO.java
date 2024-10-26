package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class DonacionDineroDTO extends ColaboracionDTO {

    private String colaborador;

    private String monto;

    private String frecuencia;

    public static DonacionDineroDTO completa(DonacionDinero donacionDinero) {

        return DonacionDineroDTO.builder()
                .id(donacionDinero.getId().toString())
                .nombre(TipoColaboracion.DONACION_DINERO.getDescription())
                .fechaHora(DateTimeParser.parseFechaHora(donacionDinero.getFechaHora()))
                .path(getPath(TipoColaboracion.DONACION_DINERO))
                .colaborador(donacionDinero.getColaborador().getUsuario().getNombre())
                .monto(donacionDinero.getMonto().toString())
                .frecuencia(donacionDinero.getFrecuencia().toString()) // TODO - Ver que onda el toString paraColaborador el Period
                .build();
    }

    public static ColaboracionDTO preview(DonacionDinero donacionDinero) {

        return ColaboracionDTO.builder()
                .id(donacionDinero.getId().toString())
                .nombre(TipoColaboracion.DONACION_DINERO.getDescription())
                .fechaHora(DateTimeParser.parseFechaHora(donacionDinero.getFechaHora()))
                .path(getPath(TipoColaboracion.DONACION_DINERO))
                .build();
    }
}

