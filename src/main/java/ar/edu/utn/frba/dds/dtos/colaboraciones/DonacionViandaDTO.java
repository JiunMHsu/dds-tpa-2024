package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class DonacionViandaDTO extends ColaboracionDTO {

    private String id;

    private String colaborador;

    private String fechaHora;

    private String esEntregada;

    private String nombreComida;

    private String pesoVianda;

    private String caloriasComida;

    private String fechaCaducidadComida;


    public static DonacionViandaDTO completa(DonacionVianda donacionVianda) { // Completa segun los atributos por la clase

        return DonacionViandaDTO
                .builder()
                .etiqueta(getAction(Colaboracion.DONACION_VIANDAS))
                .path(getPath(Colaboracion.DONACION_VIANDAS))
                .id(donacionVianda.getId().toString())
                .colaborador(donacionVianda.getColaborador().getUsuario().getNombre())
                .fechaHora(DateTimeParser.parseFechaHora(donacionVianda.getFechaHora()))
                .esEntregada(donacionVianda.getEsEntregada() ? "Entregada" : "No entregada")
                .nombreComida(donacionVianda.getVianda().getComida().getNombre())
                .pesoVianda(donacionVianda.getVianda().getPeso().toString())
                .caloriasComida(String.valueOf(donacionVianda.getVianda().getComida().getCalorias()))
                .fechaCaducidadComida(donacionVianda.getVianda().getFechaCaducidad().toString())
                .build();
    }

    public static DonacionViandaDTO preview(DonacionVianda donacionVianda) {

        return DonacionViandaDTO
                .builder()
                .etiqueta(getAction(Colaboracion.DONACION_VIANDAS))
                .path(getPath(Colaboracion.DONACION_VIANDAS))
                .id(donacionVianda.getId().toString())
                .fechaHora(DateTimeParser.parseFechaHora(donacionVianda.getFechaHora()))
                .build();
    }
}
