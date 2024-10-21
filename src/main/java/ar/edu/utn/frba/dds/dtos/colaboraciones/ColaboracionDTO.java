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

    @Setter
    private boolean estaActivo;

    public static ColaboracionDTO fromColaboracion(Colaboracion colaboracion, boolean estaActivo) {
        return ColaboracionDTO
                .builder()
                .etiqueta(colaboracion.getDescription())
                .valor(colaboracion.toString())
                .estaActivo(estaActivo)
                .build();
    }
}
