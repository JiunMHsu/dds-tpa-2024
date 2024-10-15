package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HacerseCargoHeladeraDTO {

    private String nombre; // lo agrego xlas, tiene pinta que puede ser util

    private String colaborador;

    private String fechaHora;

    private String heladeraACargo;

    public static HacerseCargoHeladeraDTO completa(HacerseCargoHeladera hacerseCargoHeladera) {

        String nombre = "Encargarse de una Heladera";

        return HacerseCargoHeladeraDTO
                .builder()
                .nombre(nombre)
                .colaborador(hacerseCargoHeladera.getColaborador().getUsuario().getNombre())
                .fechaHora(hacerseCargoHeladera.getFechaHora().toString())
                .heladeraACargo(hacerseCargoHeladera.getHeladeraACargo().getNombre())
                .build();
    }

    public static HacerseCargoHeladeraDTO preview(HacerseCargoHeladera hacerseCargoHeladera) {

        String nombre = "Encargarse de una Heladera";

        return HacerseCargoHeladeraDTO
                .builder()
                .nombre(nombre)
                .heladeraACargo(hacerseCargoHeladera.getHeladeraACargo().getNombre())
                .build();
    }
}
