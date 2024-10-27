package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class DistribucionViandasDTO extends ColaboracionDTO {

    private String colaborador;

    private String heladeraOrigen;

    private String heladeraDestino;

    private String cantViandas;

    private String motivo;

    public static DistribucionViandasDTO completa(DistribucionViandas distribucionViandas) {

        return DistribucionViandasDTO.builder()
                .id(distribucionViandas.getId().toString())
                .nombre(TipoColaboracion.DISTRIBUCION_VIANDAS.getDescription())
                .fechaHora(DateTimeParser.parseFechaHora(distribucionViandas.getFechaHora()))
                .path(getPath(TipoColaboracion.DISTRIBUCION_VIANDAS))
                .colaborador(distribucionViandas.getColaborador().getUsuario().getNombre())
                .heladeraOrigen(distribucionViandas.getOrigen().getNombre())
                .heladeraDestino(distribucionViandas.getDestino().getNombre())
                .cantViandas(distribucionViandas.getViandas().toString())
                .motivo(distribucionViandas.getMotivo())
                .build();
    }

    public static ColaboracionDTO preview(DistribucionViandas distribucionViandas) {

        return ColaboracionDTO.builder()
                .id(distribucionViandas.getId().toString())
                .fechaHora(DateTimeParser.parseFechaHora(distribucionViandas.getFechaHora()))
                .nombre(TipoColaboracion.DISTRIBUCION_VIANDAS.getDescription())
                .path(getPath(TipoColaboracion.DISTRIBUCION_VIANDAS))
                .build();
    }
}
