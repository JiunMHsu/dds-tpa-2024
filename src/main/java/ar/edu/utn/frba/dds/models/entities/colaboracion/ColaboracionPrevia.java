package ar.edu.utn.frba.dds.models.entities.colaboracion;

import ar.edu.utn.frba.dds.models.entities.data.Documento;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColaboracionPrevia {

    private Documento documento;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDateTime fechaHora;
    private String formaDeColaboracion;
    private Integer cantidad;

    public static ColaboracionPrevia por(Documento documento,
                                         String nombre,
                                         String apellido,
                                         String email,
                                         LocalDateTime fechaHora,
                                         String formaDeColaboracion,
                                         Integer cantidad) {
        return ColaboracionPrevia
                .builder()
                .documento(documento)
                .nombre(nombre)
                .apellido(apellido)
                .email(email)
                .fechaHora(fechaHora)
                .formaDeColaboracion(formaDeColaboracion)
                .cantidad(cantidad)
                .build();
    }
}
