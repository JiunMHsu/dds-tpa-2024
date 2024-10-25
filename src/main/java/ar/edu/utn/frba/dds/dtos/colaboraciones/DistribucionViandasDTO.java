package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class DistribucionViandasDTO extends ColaboracionDTO {

    private String id;

    private String colaborador;

    private String fechaHora;

    private String heladeraOrigen;

    private String heladeraDestino;

    private String cantViandas;

    private String motivo;

    public static DistribucionViandasDTO completa(DistribucionViandas distribucionViandas) {

        return DistribucionViandasDTO
                .builder()
                .etiqueta(getAction(Colaboracion.DISTRIBUCION_VIANDAS))
                .path(getPath(Colaboracion.DISTRIBUCION_VIANDAS))
                .id(distribucionViandas.getId().toString())
                .colaborador(distribucionViandas.getColaborador().getUsuario().getNombre())
                .fechaHora(DateTimeParser.parseFechaHora(distribucionViandas.getFechaHora()))
                .heladeraOrigen(distribucionViandas.getOrigen().getNombre())
                .heladeraDestino(distribucionViandas.getDestino().getNombre())
                .cantViandas(distribucionViandas.getViandas().toString())
                .motivo(distribucionViandas.getMotivo())
                .build();
    }

    public static DistribucionViandasDTO preview(DistribucionViandas distribucionViandas) {

        return DistribucionViandasDTO
                .builder()
                .etiqueta(getAction(Colaboracion.DISTRIBUCION_VIANDAS))
                .path(getPath(Colaboracion.DISTRIBUCION_VIANDAS))
                .id(distribucionViandas.getId().toString())
                .fechaHora(DateTimeParser.parseFechaHora(distribucionViandas.getFechaHora()))
                .build();
    }
}
