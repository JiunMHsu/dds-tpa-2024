package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class DonacionViandaDTO extends ColaboracionDTO {

  private String colaborador;

  private String esEntregada;

  private String nombreComida;

  private String pesoVianda;

  private String caloriasComida;

  private String fechaCaducidadComida;


  public static DonacionViandaDTO completa(DonacionVianda donacionVianda) { // Completa segun los atributos nueva la clase

    return DonacionViandaDTO.builder()
        .id(donacionVianda.getId().toString())
        .nombre(TipoColaboracion.DONACION_VIANDAS.getDescription())
        .fechaHora(DateTimeParser.parseFechaHora(donacionVianda.getFechaHora()))
        .path(getPath(TipoColaboracion.DONACION_VIANDAS))
        .colaborador(donacionVianda.getColaborador().getUsuario().getNombre())
        .esEntregada(donacionVianda.getEsEntregada() ? "Entregada" : "No entregada")
        .nombreComida(donacionVianda.getVianda().getComida().getNombre())
        .pesoVianda(donacionVianda.getVianda().getPeso().toString())
        .caloriasComida(String.valueOf(donacionVianda.getVianda().getComida().getCalorias()))
        .fechaCaducidadComida(donacionVianda.getVianda().getFechaCaducidad().toString())
        .build();
  }

  public static ColaboracionDTO preview(DonacionVianda donacionVianda) {

    return ColaboracionDTO.builder()
        .id(donacionVianda.getId().toString())
        .nombre(TipoColaboracion.DONACION_VIANDAS.getDescription())
        .fechaHora(DateTimeParser.parseFechaHora(donacionVianda.getFechaHora()))
        .path(getPath(TipoColaboracion.DONACION_VIANDAS))
        .build();
  }
}
