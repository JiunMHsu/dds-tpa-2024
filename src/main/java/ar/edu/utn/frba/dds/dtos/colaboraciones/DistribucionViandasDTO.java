package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DistribucionViandasDTO {

    private String id;

    private String nombre; // lo agrego xlas, tiene pinta que puede ser util

    private String colaborador;

    private String fechaHora;

    private String heladeraOrigen;

    private String heladeraDestino;

    private String cantViandas;

    private String motivo;

    public static DistribucionViandasDTO completa(DistribucionViandas distribucionViandas) {

        String nombre = "Distribuir Viandas"; // Lo dejo asi medio tosco quizas en un futuro c pueda hacer dinamico
        // En caso de que c cambien los nombres de los metodos de contribuir

        return DistribucionViandasDTO
                .builder()
                .id(distribucionViandas.getId().toString())
                .nombre(nombre)
                .colaborador(distribucionViandas.getColaborador().getUsuario().getNombre())
                .fechaHora(distribucionViandas.getFechaHora().toString())
                .heladeraOrigen(distribucionViandas.getOrigen().getNombre())
                .heladeraDestino(distribucionViandas.getDestino().getNombre())
                .cantViandas(distribucionViandas.getViandas().toString())
                .motivo(distribucionViandas.getMotivo())
                .build();
    }

    public static DistribucionViandasDTO preview(DistribucionViandas distribucionViandas) {

        String nombre = "Distribuir Viandas"; // Lo dejo asi medio tosco quizas en un futuro c pueda hacer dinamico
        // En caso de que c cambien los nombres de los metodos de contribuir

        return DistribucionViandasDTO
                .builder()
                .id(distribucionViandas.getId().toString())
                .nombre(nombre)
                .heladeraOrigen(distribucionViandas.getOrigen().getNombre())
                .heladeraDestino(distribucionViandas.getDestino().getNombre())
                .cantViandas(distribucionViandas.getViandas().toString())
                .motivo(distribucionViandas.getMotivo())
                .build();
    }
}
