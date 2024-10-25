package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class DonacionDineroDTO extends ColaboracionDTO {

    private String id;

    private String colaborador;

    private String fechaHora;

    private String monto;

    private String frecuencia;

    public static DonacionDineroDTO completa(DonacionDinero donacionDinero) {

        return DonacionDineroDTO
                .builder()
                .etiqueta(getAction(Colaboracion.DONACION_DINERO))
                .path(getPath(Colaboracion.DONACION_DINERO))
                .id(donacionDinero.getId().toString())
                .colaborador(donacionDinero.getColaborador().getUsuario().getNombre())
                .fechaHora(DateTimeParser.parseFechaHora(donacionDinero.getFechaHora()))
                .monto(donacionDinero.getMonto().toString())
                .frecuencia(donacionDinero.getFrecuencia().toString()) // TODO - Ver que onda el toString paraColaborador el Period
                .build();
    }

    public static DonacionDineroDTO preview(DonacionDinero donacionDinero) {

        return DonacionDineroDTO
                .builder()
                .etiqueta(getAction(Colaboracion.DONACION_DINERO))
                .path(getPath(Colaboracion.DONACION_DINERO))
                .id(donacionDinero.getId().toString())
                .fechaHora(DateTimeParser.parseFechaHora(donacionDinero.getFechaHora()))
                .build();
    }
}

