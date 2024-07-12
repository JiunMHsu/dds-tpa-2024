package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.utils.GeneradorDeCodigosTarjeta;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TarjetaColaborador implements Tarjeta {

  private String codigo;
  private Colaborador duenio;

  public static TarjetaColaborador with(Colaborador duenio) {
    return TarjetaColaborador
        .builder()
        .codigo(GeneradorDeCodigosTarjeta.generar())
        .duenio(duenio)
        .build();
  }

  public Boolean puedeUsar(Heladera heladera) {
    return heladera.estaActiva() && heladera.haySolicitudDeAperturaPor(this);
  }

  public void registrarUso(Heladera heladera) {
  }
}
