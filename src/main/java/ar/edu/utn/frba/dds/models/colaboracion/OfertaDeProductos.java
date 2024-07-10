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
  private Integer puntosNecesarios;
  private RubroOferta rubro;
  private Imagen imagen;

  public static OfertaDeProductos with(Colaborador colaborador,
                                       String nombre,
                                       Integer puntosNecesarios,
                                       RubroOferta rubro,
                                       Imagen imagen) {
    return OfertaDeProductos
        .builder()
        .colaborador(colaborador)
        .fechaOferta(LocalDate.now())
        .nombre(nombre)
        .puntosNecesarios(puntosNecesarios)
        .rubro(rubro)
        .imagen(imagen)
        .build();
  }

  public static OfertaDeProductos with(Colaborador colaborador,
                                       String nombre,
                                       Integer puntosNecesarios) {
    return OfertaDeProductos
        .builder()
        .colaborador(colaborador)
        .fechaOferta(LocalDate.now())
        .nombre(nombre)
        .puntosNecesarios(puntosNecesarios)
        .build();
  }
}
