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
            case DONACION_VIANDAS -> "donacion-dinero";
            case DONACION_DINERO -> "donacion-vianda";
            case DISTRIBUCION_VIANDAS -> "registro-persona-vulnerable";
            case ENTREGA_VIANDA -> "entrega-viandas";
            case HACERSE_CARGO_HELADERA -> "distribucion-viandas";
            case OFERTA_DE_PRODUCTOS -> "oferta-producto-servicio";
            case REPARTO_DE_TARJETAS -> "encargarse-por-heladeras";
        };
    }
}
