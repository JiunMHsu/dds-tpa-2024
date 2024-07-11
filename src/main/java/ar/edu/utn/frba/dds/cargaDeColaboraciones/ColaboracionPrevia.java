package ar.edu.utn.frba.dds.cargaDeColaboraciones;

import ar.edu.utn.frba.dds.models.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.data.Documento;
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
  private LocalDateTime fechaDeColaboracion;
  private Colaboracion formaDeColaboracion;
  private Integer cantidad;

  public static ColaboracionPrevia of(Documento documento,
                                      String nombre,
                                      String apellido,
                                      String email,
                                      LocalDateTime fechaDeColaboracion,
                                      Colaboracion formaDeColaboracion,
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
