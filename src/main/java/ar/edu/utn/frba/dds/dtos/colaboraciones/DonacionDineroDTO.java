package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DonacionDineroDTO {

    private String id;

    private String nombre; // lo agrego xlas, tiene pinta que puede ser util

    private String colaborador;

    private String fechaHora;

    private String monto;

    private String frecuencia;

    public static DonacionDineroDTO completa(DonacionDinero donacionDinero) {

        String nombre = "Donar Dinero"; // Lo dejo asi medio tosco quizas en un futuro c pueda hacer dinamico
                                        // En caso de que c cambien los nombres de los metodos de contribuir

        return DonacionDineroDTO
                .builder()
                .id(donacionDinero.getId().toString())
                .nombre(nombre)
                .colaborador(donacionDinero.getColaborador().getUsuario().getNombre())
                .fechaHora(donacionDinero.getFechaHora().toString())
                .monto(donacionDinero.getMonto().toString())
                .frecuencia(donacionDinero.getFrecuencia().toString()) // TODO - Ver que onda el toString con el Period
                .build();
    }

    public static DonacionDineroDTO preview(DonacionDinero donacionDinero) { // TODO - ver si se ajusta a la vista

        String nombre = "Donar Dinero"; // Lo dejo asi medio tosco quizas en un futuro c pueda hacer dinamico
                                        // En caso de que c cambien los nombres de los metodos de contribuir

        return DonacionDineroDTO
                .builder()
                .id(donacionDinero.getId().toString())
                .nombre(nombre)
                .monto(donacionDinero.getMonto().toString())
                .frecuencia(donacionDinero.getFrecuencia().toString())
                .build();
    }
}

