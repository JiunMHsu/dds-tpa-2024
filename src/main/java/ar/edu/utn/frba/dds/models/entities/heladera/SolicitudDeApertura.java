package ar.edu.utn.frba.dds.models.entities.heladera;

import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Representa una solicitud de apertura de una {@link Heladera}.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "solicitud_apertura")
public class SolicitudDeApertura extends EntidadPersistente {

  @ManyToOne
  @JoinColumn(name = "tarjeta_id", nullable = false)
  private TarjetaColaborador tarjeta;

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  @Column(name = "fecha_hora")
  private LocalDateTime fechaHora;

  @Enumerated(EnumType.STRING)
  @Column(name = "motivo")
  private MotivoApertura motivo;

  @Enumerated(EnumType.STRING)
  @Column(name = "operacion")
  private OperacionApertura operacion;

  /**
   * Crea una solicitud de apertura.
   *
   * @param tarjeta   Tarjeta del colaborador que solicita la apertura.
   * @param heladera  Heladera que se solicita abrir.
   * @param fechaHora Fecha y hora en la que se realizó la solicitud.
   * @param motivo    Motivo de la solicitud.
   * @param operacion Operación de la solicitud.
   * @return Solicitud de apertura creada.
   */
  public static SolicitudDeApertura por(TarjetaColaborador tarjeta,
                                        Heladera heladera,
                                        LocalDateTime fechaHora,
                                        MotivoApertura motivo,
                                        OperacionApertura operacion) {
    return SolicitudDeApertura
        .builder()
        .tarjeta(tarjeta)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .motivo(motivo)
        .operacion(operacion)
        .build();
  }

  /**
   * Crea una solicitud de apertura por donación de viandas.
   *
   * @param tarjeta  Tarjeta del colaborador que solicita la apertura.
   * @param heladera Heladera que se solicita abrir.
   * @return Solicitud de apertura creada.
   */
  public static SolicitudDeApertura paraDonacionViandas(TarjetaColaborador tarjeta,
                                                        Heladera heladera) {
    return SolicitudDeApertura.por(
        tarjeta,
        heladera,
        LocalDateTime.now(),
        MotivoApertura.DONACION_VIANDA,
        OperacionApertura.INGRESO_VIANDAS
    );
  }

  /**
   * Crea una solicitud de apertura por distribución de viandas.
   *
   * @param tarjeta   Tarjeta del colaborador que solicita la apertura.
   * @param heladera  Heladera que se solicita abrir.
   * @param operacion Operación de la solicitud.
   * @return Solicitud de apertura creada.
   */
  public static SolicitudDeApertura paraDistribucionDeViandas(TarjetaColaborador tarjeta,
                                                              Heladera heladera,
                                                              OperacionApertura operacion) {
    return SolicitudDeApertura.por(
        tarjeta,
        heladera,
        LocalDateTime.now(),
        MotivoApertura.DISTRIBUCION_VIANDA,
        operacion
    );
  }

  /**
   * Indica si la solicitud de apertura está vigente.
   *
   * @param cantTiempo Cantidad de tiempo.
   * @param unidad     Unidad de tiempo. (usar {@link ChronoUnit})
   * @return {@code true} si la solicitud de apertura está vigente, {@code false} en caso contrario.
   */
  public boolean estaVigente(long cantTiempo, TemporalUnit unidad) {
    return this.fechaHora.plus(cantTiempo, unidad).isAfter(LocalDateTime.now());
  }

}
