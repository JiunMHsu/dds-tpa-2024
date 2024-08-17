package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.repository.heladera.SolicitudDeAperturaRepository;
import ar.edu.utn.frba.dds.utils.GeneradorDeCodigosTarjeta;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TarjetaColaborador implements Tarjeta {

  private String codigo;
  private Colaborador duenio;
  private LocalDate fechaAlta;
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

  public Boolean puedeUsarseEn(Heladera heladera) {
    List<SolicitudDeApertura> solicitudes = SolicitudDeAperturaRepository
        .obtenerPorTarjeta(codigo, LocalDateTime.now().minusHours(3));

    SolicitudDeApertura solicitud = solicitudes.stream()
        .filter(s -> s.getHeladera().equals(heladera))
        .findFirst().orElse(null);

    return this.esActiva && (solicitud != null);
  }
}
