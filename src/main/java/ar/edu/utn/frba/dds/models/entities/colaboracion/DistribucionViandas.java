package ar.edu.utn.frba.dds.models.entities.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Distribución de viandas de una {@link Heladera} a otra.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "distribucion_viandas")
public class DistribucionViandas extends EntidadPersistente {

  @ManyToOne
  @JoinColumn(name = "colaborador_id", nullable = false)
  private Colaborador colaborador;

  @Column(name = "fecha_hora", columnDefinition = "DATETIME", nullable = false)
  private LocalDateTime fechaHora;

  @ManyToOne
  @JoinColumn(name = "heladera_origen_id") // nullable por compatibilidad
  private Heladera origen;

  @ManyToOne
  @JoinColumn(name = "heladera_destino_id") // nullable por compatibilidad
  private Heladera destino;

  @Column(name = "cant_viandas", columnDefinition = "SMALLINT", nullable = false)
  private Integer viandas;

  @Column(name = "motivo", columnDefinition = "TEXT")
  private String motivo;

  @Setter
  @Enumerated(EnumType.STRING)
  @Column(name = "estado", nullable = false)
  private EstadoDistribucion estado;

  @ManyToOne
  @JoinColumn(name = "solicitud_apertura_origen_id") // nullable por compatibilidad
  private SolicitudDeApertura solicitudAperturaOrigen;

  @OneToOne
  @JoinColumn(name = "solicitud_apertura_destino_id") // nullable por compatibilidad
  private SolicitudDeApertura solicitudAperturaDestino;

  /**
   * Crea una distribución de viandas nueva, pendiente a ser realizada.
   *
   * @param colaborador                {@link Colaborador} que realiza la distribución
   * @param origen                     {@link Heladera} de origen de las viandas
   * @param destino                    {@link Heladera} de destino de las viandas
   * @param viandas                    cantidad de viandas a distribuir
   * @param motivo                     motivo de la distribución
   * @param solicitudDeAperturaOrigen  {@link SolicitudDeApertura} de la heladera de origen
   * @param solicitudDeAperturaDestino {@link SolicitudDeApertura} de la heladera de destino
   * @return distribución de viandas
   */
  public static DistribucionViandas por(Colaborador colaborador,
                                        Heladera origen,
                                        Heladera destino,
                                        Integer viandas,
                                        String motivo,
                                        SolicitudDeApertura solicitudDeAperturaOrigen,
                                        SolicitudDeApertura solicitudDeAperturaDestino) {
    return DistribucionViandas.builder()
        .colaborador(colaborador)
        .fechaHora(LocalDateTime.now())
        .origen(origen)
        .destino(destino)
        .viandas(viandas)
        .motivo(motivo)
        .estado(EstadoDistribucion.PENDIENTE)
        .solicitudAperturaOrigen(solicitudDeAperturaOrigen)
        .solicitudAperturaDestino(solicitudDeAperturaDestino)
        .build();
  }

  /**
   * Crea una distribución de viandas completada.
   *
   * @param colaborador {@link Colaborador} que realizó la distribución
   * @param fechaHora   fecha y hora de la distribución
   * @param viandas     cantidad de viandas distribuidas
   * @return distribución de viandas
   */
  public static DistribucionViandas por(Colaborador colaborador,
                                        LocalDateTime fechaHora,
                                        Integer viandas) {
    return DistribucionViandas.builder()
        .colaborador(colaborador)
        .fechaHora(fechaHora)
        .viandas(viandas)
        .estado(EstadoDistribucion.COMPLETADA)
        .build();
  }
}