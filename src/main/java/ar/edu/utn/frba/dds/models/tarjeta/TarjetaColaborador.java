package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.utils.GeneradorDeCodigosTarjeta;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TarjetaColaborador implements ITarjeta {

  private String codigo;
  private Colaborador duenio;

  public static TarjetaColaborador with(Colaborador duenio) {
    return TarjetaColaborador
        .builder()
        .codigo(GeneradorDeCodigosTarjeta.generar())
        .duenio(duenio)
        .build();
  }

  // TODO
  public void registrarUso(Heladera heladera) {

    // validar el uso (buscar solicitud)
  }
}
