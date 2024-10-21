package ar.edu.utn.frba.dds.models.entities.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import java.util.List;

public enum TipoColaborador {
    HUMANO,
    JURIDICO;

    public List<Colaboracion> colaboracionesPermitidas() {
        return switch (this) {
            case HUMANO -> List.of(
                    Colaboracion.DONACION_DINERO,
                    Colaboracion.DONACION_VIANDAS,
                    Colaboracion.DISTRIBUCION_VIANDAS,
                    Colaboracion.ENTREGA_VIANDA,
                    Colaboracion.REPARTO_DE_TARJETAS);
            case JURIDICO -> List.of(
                    Colaboracion.DONACION_DINERO,
                    Colaboracion.OFERTA_DE_PRODUCTOS,
                    Colaboracion.HACERSE_CARGO_HELADERA);
        };
    }
}
