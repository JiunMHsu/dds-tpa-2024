package ar.edu.utn.frba.dds.models.entities.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * Colaboración de un {@link Colaborador} que dona una {@link Vianda}.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donacion_vianda")
public class DonacionVianda extends EntidadPersistente {

  @ManyToOne
  @JoinColumn(name = "colaborador_id", nullable = false)
  private Colaborador colaborador;

  @Column(name = "fecha_hora", columnDefinition = "DATETIME", nullable = false)
  private LocalDateTime fechaHora;

  @OneToOne
  @JoinColumn(name = "vianda_id") // nullable por compatibilidad
  private Vianda vianda;

  @Setter
  @Column(name = "es_entregada", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private Boolean esEntregada;

  @Setter
  @ManyToOne
  @JoinColumn(name = "heladera_id")
  private Heladera heladera; // nullable por compatibilidad

  @OneToOne
  @JoinColumn(name = "solicitud_apertura_id") // nullable por compatibilidad
  private SolicitudDeApertura solicitudDeApertura;

  /**
   * Crea una donación de vianda.
   *
   * @param colaborador         {@link Colaborador} que dona la vianda
   * @param fechaHora           fecha y hora de la donación
   * @param vianda              {@link Vianda} donada
   * @param heladera            {@link Heladera} la cual se donará la vianda
   * @param solicitudDeApertura {@link SolicitudDeApertura} asociada a la donación
   * @return donación de vianda
   */
  public static DonacionVianda por(Colaborador colaborador,
                                   LocalDateTime fechaHora,
                                   Vianda vianda,
                                   Heladera heladera,
                                   SolicitudDeApertura solicitudDeApertura) {
    return DonacionVianda.builder()
        .colaborador(colaborador)
        .fechaHora(fechaHora)
        .vianda(vianda)
        .esEntregada(false)
        .heladera(heladera)
        .solicitudDeApertura(solicitudDeApertura)
        .build();
  }

  /**
   * Crea una donación de vianda sin especificar la vianda donada.
   * Constructor para registrar donaciones previas.
   *
   * @param colaborador {@link Colaborador} que dona la vianda
   * @param fechaHora   fecha y hora de la donación
   * @return donación de vianda
   */
  public static DonacionVianda por(Colaborador colaborador,
                                   LocalDateTime fechaHora) {
    return DonacionVianda.builder()
        .colaborador(colaborador)
        .fechaHora(fechaHora)
        .esEntregada(true)
        .build();
  }
}