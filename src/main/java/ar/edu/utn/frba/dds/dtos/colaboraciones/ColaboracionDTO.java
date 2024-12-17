package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.*;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Builder
public class ColaboracionDTO {

    protected String id;

    protected String nombre;

    protected String fechaHora;

    protected String path;

    protected static String getPath(TipoColaboracion colaboracion) {
        return switch (colaboracion) {
            case DONACION_VIANDAS -> "donacion-vianda";
            case DONACION_DINERO -> "donacion-dinero";
            case DISTRIBUCION_VIANDAS -> "distribucion-viandas";
            case ENTREGA_VIANDA -> "entrega-viandas";
            case HACERSE_CARGO_HELADERA -> "encargarse-de-heladeras";
            case OFERTA_DE_PRODUCTOS -> "oferta-producto-servicio";
            case REPARTO_DE_TARJETAS -> "registro-persona-vulnerable";
        };
    }

    public static ColaboracionDTO toDTO(Object colaboracion) {

        if (colaboracion instanceof DonacionVianda) {
            return DonacionViandaDTO.preview((DonacionVianda) colaboracion);
        } else if (colaboracion instanceof DonacionDinero) {
            return DonacionDineroDTO.preview((DonacionDinero) colaboracion);
        } else if (colaboracion instanceof DistribucionViandas) {
            return DistribucionViandasDTO.preview((DistribucionViandas) colaboracion);
        } else if (colaboracion instanceof EntregaViandas) {
            return EntregaViandasDTO.preview((EntregaViandas) colaboracion);
        } else if (colaboracion instanceof HacerseCargoHeladera) {
            return HacerseCargoHeladeraDTO.preview((HacerseCargoHeladera) colaboracion);
        } else if (colaboracion instanceof OfertaDeProductos) {
            return OfertaDeProductosDTO.preview((OfertaDeProductos) colaboracion);
        } else if (colaboracion instanceof RepartoDeTarjetas) {
            return RepartoDeTarjetasDTO.preview((RepartoDeTarjetas) colaboracion);
        } else {
            throw new IllegalArgumentException("Tipo de colaboración desconocido: " + colaboracion.getClass());
        }
    }
}
