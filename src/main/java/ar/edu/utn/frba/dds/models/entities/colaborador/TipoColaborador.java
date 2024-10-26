package ar.edu.utn.frba.dds.models.entities.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import java.util.List;

public enum TipoColaborador {
    HUMANO,
    JURIDICO;

    public List<TipoColaboracion> colaboracionesPermitidas() {
        return switch (this) {
            case HUMANO -> List.of(
                    TipoColaboracion.DONACION_DINERO,
                    TipoColaboracion.DONACION_VIANDAS,
                    TipoColaboracion.DISTRIBUCION_VIANDAS,
                    TipoColaboracion.ENTREGA_VIANDA,
                    TipoColaboracion.REPARTO_DE_TARJETAS);

            case JURIDICO -> List.of(
                    TipoColaboracion.DONACION_DINERO,
                    TipoColaboracion.OFERTA_DE_PRODUCTOS,
                    TipoColaboracion.HACERSE_CARGO_HELADERA);
        };
    }
}
