package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ColaboracionDTO {

    private String etiqueta;
    private String valor;
    private String path;

    @Setter
    private boolean estaActivo;

    public static ColaboracionDTO configOption(Colaboracion colaboracion, boolean estaActivo) {
        return ColaboracionDTO
                .builder()
                .etiqueta(colaboracion.getDescription())
                .valor(colaboracion.toString())
                .estaActivo(estaActivo)
                .build();
    }

    public static ColaboracionDTO redirectable(Colaboracion colaboracion) {
        return ColaboracionDTO
                .builder()
                .etiqueta(getAction(colaboracion))
                .path(getPath(colaboracion))
                .estaActivo(true)
                .build();
    }

    private static String getAction(Colaboracion colaboracion) {
        return switch (colaboracion) {
            case DONACION_VIANDAS -> "Donar Vianda";
            case DONACION_DINERO -> "Donar Dinero";
            case DISTRIBUCION_VIANDAS -> "Distribuir Viandas";
            case ENTREGA_VIANDA -> "Entregar Viandas";
            case HACERSE_CARGO_HELADERA -> "Encargarse de Heladeras";
            case OFERTA_DE_PRODUCTOS -> "Ofrecer Producto / Servicio";
            case REPARTO_DE_TARJETAS -> "Registrar Persona Vulnerable";
        };
    }

    private static String getPath(Colaboracion colaboracion) {
        return switch (colaboracion) {
            case DONACION_VIANDAS -> "donacion-dinero";
            case DONACION_DINERO -> "donacion-vianda";
            case DISTRIBUCION_VIANDAS -> "registro-persona-vulnerable";
            case ENTREGA_VIANDA -> "entrega-viandas";
            case HACERSE_CARGO_HELADERA -> "distribucion-viandas";
            case OFERTA_DE_PRODUCTOS -> "oferta-producto-servicio";
            case REPARTO_DE_TARJETAS -> "encargarse-de-heladeras";
        };
    }
}
