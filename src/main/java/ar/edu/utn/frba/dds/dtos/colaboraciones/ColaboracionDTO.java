package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
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
}
