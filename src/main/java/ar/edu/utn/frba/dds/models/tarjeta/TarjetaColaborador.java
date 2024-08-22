package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.utils.GeneradorDeCodigosTarjeta;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class TarjetaColaborador implements Tarjeta {

  private String codigo;
  private Colaborador duenio;
  private LocalDate fechaAlta;

  @Setter
  private Boolean esActiva;

  public static TarjetaColaborador de(String codigo,
                                      Colaborador duenio,
                                      LocalDate fechaAlta,
                                      Boolean esActiva) {
    return TarjetaColaborador
        .builder()
        .codigo(codigo)
        .duenio(duenio)
        .fechaAlta(fechaAlta)
        .esActiva(esActiva)
        .build();
  }

  public static TarjetaColaborador de(Colaborador duenio) {
    return TarjetaColaborador
        .builder()
        .codigo(GeneradorDeCodigosTarjeta.generar())
        .duenio(duenio)
        .fechaAlta(LocalDate.now())
        .esActiva(true)
        .build();
  }

}
