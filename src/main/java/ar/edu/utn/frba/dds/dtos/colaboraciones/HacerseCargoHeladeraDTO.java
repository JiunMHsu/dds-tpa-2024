package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class HacerseCargoHeladeraDTO extends ColaboracionDTO {

    private String id;

    private String colaborador;

    private String fechaHora;

    private String heladeraACargo;

    public static HacerseCargoHeladeraDTO completa(HacerseCargoHeladera hacerseCargoHeladera) {

        return HacerseCargoHeladeraDTO
                .builder()
                .etiqueta(getAction(Colaboracion.HACERSE_CARGO_HELADERA))
                .path(getPath(Colaboracion.HACERSE_CARGO_HELADERA))
                .id(hacerseCargoHeladera.getId().toString())
                .colaborador(hacerseCargoHeladera.getColaborador().getUsuario().getNombre())
                .fechaHora(DateTimeParser.parseFechaHora(hacerseCargoHeladera.getFechaHora()))
                .heladeraACargo(hacerseCargoHeladera.getHeladeraACargo().getNombre())
                .build();
    }

    public static HacerseCargoHeladeraDTO preview(HacerseCargoHeladera hacerseCargoHeladera) { // TODO - ver si se ajusta a la vista

        return HacerseCargoHeladeraDTO
                .builder()
                .etiqueta(getAction(Colaboracion.HACERSE_CARGO_HELADERA))
                .path(getPath(Colaboracion.HACERSE_CARGO_HELADERA))
                .id(hacerseCargoHeladera.getId().toString())
                .fechaHora(DateTimeParser.parseFechaHora(hacerseCargoHeladera.getFechaHora()))
                .build();
    }
}
