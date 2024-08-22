package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.data.Imagen;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OfertaDeProductos {

  private Colaborador colaborador;
  private LocalDate fechaOferta;
  private String nombre;
  private Double puntosNecesarios;
  private RubroOferta rubro;
  private Imagen imagen;

  public static OfertaDeProductos por(Colaborador colaborador,
                                      LocalDate fechaOferta,
                                      String nombre,
                                      Double puntosNecesarios,
                                      RubroOferta rubro,
                                      Imagen imagen) {
    return OfertaDeProductos
        .builder()
        .colaborador(colaborador)
        .fechaOferta(fechaOferta)
        .nombre(nombre)
        .puntosNecesarios(puntosNecesarios)
        .rubro(rubro)
        .imagen(imagen)
        .build();
  }

  public static OfertaDeProductos por(Colaborador colaborador,
                                      String nombre,
                                      Double puntosNecesarios) {
    return OfertaDeProductos
        .builder()
        .colaborador(colaborador)
        .nombre(nombre)
        .puntosNecesarios(puntosNecesarios)
        .build();
  }

}
