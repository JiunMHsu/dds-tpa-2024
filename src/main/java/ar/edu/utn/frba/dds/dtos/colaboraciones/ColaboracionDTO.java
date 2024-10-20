package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColaboracionDTO {
    private String etiqueta;
    private String valor;

    public static ColaboracionDTO fromColaboracion(Colaboracion colaboracion) {
        return ColaboracionDTO
                .builder()
                .etiqueta(colaboracion.getDescription())
                .valor(colaboracion.toString())
                .build();
    }
}
