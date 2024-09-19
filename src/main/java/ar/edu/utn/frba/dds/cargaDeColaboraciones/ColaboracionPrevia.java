package ar.edu.utn.frba.dds.cargaDeColaboraciones;

import ar.edu.utn.frba.dds.models.data.Documento;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ColaboracionPrevia {

    private Documento documento;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaDeColaboracion;
    private String formaDeColaboracion;
    private Integer cantidad;

    public static ColaboracionPrevia of(Documento documento,
                                        String nombre,
                                        String apellido,
                                        String email,
                                        LocalDate fechaDeColaboracion,
                                        String formaDeColaboracion,
                                        Integer cantidad) {
        return ColaboracionPrevia
                .builder()
                .documento(documento)
                .nombre(nombre)
                .apellido(apellido)
                .email(email)
                .fechaDeColaboracion(fechaDeColaboracion)
                .formaDeColaboracion(formaDeColaboracion)
                .cantidad(cantidad)
                .build();
    }
}
