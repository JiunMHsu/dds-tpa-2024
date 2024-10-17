package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjetas;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RepartoDeTarjetasDTO {

    private String id;

    private String nombre;

    private String colaborador;

    private String fechaHora;

    private String tarjeta;

    private String personaVulnerable;

    public static RepartoDeTarjetasDTO completa(RepartoDeTarjetas repartoDeTarjetas) {

        String nombre = "Reparto de Tarjetas";

        return RepartoDeTarjetasDTO
                .builder()
                .id(repartoDeTarjetas.getId().toString())
                .nombre(nombre)
                .colaborador(repartoDeTarjetas.getColaborador().getUsuario().getNombre())
                .fechaHora(repartoDeTarjetas.getFechaHora().toString())
                .tarjeta(repartoDeTarjetas.getTarjeta().getCodigo())
                .personaVulnerable(repartoDeTarjetas.getPersonaVulnerable().getNombre())
                .build();
    }

    public static RepartoDeTarjetasDTO preview(RepartoDeTarjetas repartoDeTarjetas) { // TODO - ver si se ajusta a la vista

        String nombre = "Reparto de Tarjetas";

        return RepartoDeTarjetasDTO
                .builder()
                .id(repartoDeTarjetas.getId().toString())
                .nombre(nombre)
                .fechaHora(repartoDeTarjetas.getFechaHora().toString())
                .tarjeta(repartoDeTarjetas.getTarjeta().getCodigo())
                .build();
    }
}

