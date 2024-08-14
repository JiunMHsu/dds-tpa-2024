package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.repository.heladera.SolicitudDeAperturaRepository;
import ar.edu.utn.frba.dds.utils.GeneradorDeCodigosTarjeta;
import java.time.LocalDateTime;
import java.util.List;
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

  public Boolean puedeUsarseEn(Heladera heladera) {
    List<SolicitudDeApertura> solicitudes = SolicitudDeAperturaRepository
        .obtenerPorTarjeta(codigo, LocalDateTime.now().minusHours(3));

    SolicitudDeApertura solicitud = solicitudes.stream()
        .filter(s -> s.getHeladera().equals(heladera))
        .findFirst().orElse(null);

    return solicitud != null;
  }
}
