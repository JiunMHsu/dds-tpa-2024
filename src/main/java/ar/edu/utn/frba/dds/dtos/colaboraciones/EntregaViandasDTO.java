package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.EntregaViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class EntregaViandasDTO extends ColaboracionDTO {

  private String colaborador;

  private String fechaHora;

  private String destino;

  private String viandas;

  public static EntregaViandasDTO completa(EntregaViandas entregaViandas) {
    return EntregaViandasDTO.builder()
        .id(entregaViandas.getId().toString())
        .nombre(TipoColaboracion.ENTREGA_VIANDA.getDescription())
        .fechaHora(DateTimeParser.parseFechaHora(entregaViandas.getFechaHora()))
        .path(getPath(TipoColaboracion.ENTREGA_VIANDA))
        .colaborador(entregaViandas.getColaborador().getUsuario().getNombre())
        .destino(entregaViandas.getDestino().getNombre())
        .viandas(entregaViandas.getViandas().toString())
        .build();
  }

  public static ColaboracionDTO preview(EntregaViandas entregaViandas) {

    return ColaboracionDTO.builder()
        .id(entregaViandas.getId().toString())
        .fechaHora(DateTimeParser.parseFechaHora(entregaViandas.getFechaHora()))
        .nombre(TipoColaboracion.ENTREGA_VIANDA.getDescription())
        .path(getPath(TipoColaboracion.ENTREGA_VIANDA))
        .build();
  }
}
