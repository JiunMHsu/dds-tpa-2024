package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DistribucionViandasDTO extends ColaboracionDTO {

    private String id;

    private String colaborador;

    private String fechaHora;

    private String heladeraOrigen;

    private String heladeraDestino;

    private String cantViandas;

    private String motivo;

    public static DistribucionViandasDTO completa(DistribucionViandas distribucionViandas) {

        redirectable(Colaboracion.DISTRIBUCION_VIANDAS);
        return DistribucionViandasDTO
                .builder()
                .id(distribucionViandas.getId().toString())
                .colaborador(distribucionViandas.getColaborador().getUsuario().getNombre())
                .fechaHora(distribucionViandas.getFechaHora().toString())
                .heladeraOrigen(distribucionViandas.getOrigen().getNombre())
                .heladeraDestino(distribucionViandas.getDestino().getNombre())
                .cantViandas(distribucionViandas.getViandas().toString())
                .motivo(distribucionViandas.getMotivo())
                .build();
    }

    public static DistribucionViandasDTO preview(DistribucionViandas distribucionViandas) {

        redirectable(Colaboracion.DISTRIBUCION_VIANDAS);
        return DistribucionViandasDTO
                .builder()
                .id(distribucionViandas.getId().toString())
                .heladeraOrigen(distribucionViandas.getOrigen().getNombre())
                .heladeraDestino(distribucionViandas.getDestino().getNombre())
                .cantViandas(distribucionViandas.getViandas().toString())
                .motivo(distribucionViandas.getMotivo())
                .build();
    }
}
