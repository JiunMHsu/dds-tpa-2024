package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DonacionViandaDTO {

    private String nombre; // lo agrego xlas, tiene pinta que puede ser util

    private String colaborador;

    private String fechaHora;

    private String esEntregada;

    private String nombreComida;

    private String pesoVianda;

    private String caloriasComida;

    private String fechaCaducidadComida;


    public static DonacionViandaDTO completa(DonacionVianda donacionVianda) { // Completa segun los atributos de la clase

        String nombre = "Donar Vianda"; // Lo dejo asi medio tosco quizas en un futuro c pueda hacer dinamico
                                         // En caso de que c cambien los nombres de los metodos de contribuir

        return DonacionViandaDTO
                .builder()
                .nombre(nombre)
                .colaborador(donacionVianda.getColaborador().getUsuario().getNombre())
                .fechaHora(donacionVianda.getFechaHora().toString())
                .esEntregada(donacionVianda.getEsEntregada() ? "Entregada" : "No entregada")
                .nombreComida(donacionVianda.getVianda().getComida().getNombre())
                .pesoVianda(donacionVianda.getVianda().getPeso().toString())
                .caloriasComida(String.valueOf(donacionVianda.getVianda().getComida().getCalorias()))
                .fechaCaducidadComida(donacionVianda.getVianda().getFechaCaducidad().toString())
                .build();
    }

    public static DonacionViandaDTO preview(DonacionVianda donacionVianda) {

        String nombre = "Donar Vianda "; // Lo dejo asi medio tosco quizas en un futuro c pueda hacer dinamico
                                         // En caso de que c cambien los nombres de los metodos de contribuir

        return DonacionViandaDTO
                .builder()
                .nombreComida(donacionVianda.getVianda().getComida().getNombre())
                .pesoVianda(donacionVianda.getVianda().getPeso().toString())
                .caloriasComida(String.valueOf(donacionVianda.getVianda().getComida().getCalorias()))
                .fechaCaducidadComida(donacionVianda.getVianda().getFechaCaducidad().toString())
                .build();
    }
}
