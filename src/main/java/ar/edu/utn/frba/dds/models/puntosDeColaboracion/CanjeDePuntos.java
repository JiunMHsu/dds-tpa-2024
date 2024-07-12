package ar.edu.utn.frba.dds.models.puntosDeColaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CanjeDePuntos {
  private Colaborador colaborador;
  private LocalDate fechaCanjeo;
  private Integer puntosCanjeados;
  private Integer puntosRestantes;
  private OfertaDeProductos oferta;

  public static CanjeDePuntos with(Colaborador colaborador,
                                   LocalDate fechaCanjeo,
                                   Integer puntosCanjeados,
                                   Integer puntosRestantes,
                                   OfertaDeProductos oferta) {
    return CanjeDePuntos
        .builder()
        .colaborador(colaborador)
        .fechaCanjeo(fechaCanjeo)
        .puntosCanjeados(puntosCanjeados)
        .puntosRestantes(puntosRestantes)
        .oferta(oferta)
        .build();
  }
}
