package ar.edu.utn.frba.dds.models.entities.heladera;

import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Representa el retiro de una vianda de una heladera por parte de una persona vulnerable.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "retiro_vianda")
public class RetiroDeVianda extends EntidadPersistente {

  @ManyToOne
  @JoinColumn(name = "tarjeta_id", nullable = false)
  private TarjetaPersonaVulnerable tarjetaPersonaVulnerable;

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  @Column(name = "fecha_hora", columnDefinition = "DATETIME")
  private LocalDateTime fechaHora;

  /**
   * Crea un retiro de vianda.
   *
   * @param tarjetaPersonaVulnerable Tarjeta de la persona que retira la vianda.
   * @param heladera                 Heladera de la que se retira la vianda.
   * @param fechaHora                Fecha y hora en la que se realizó el retiro.
   * @return Retiro de vianda creado.
   */
  public static RetiroDeVianda por(TarjetaPersonaVulnerable tarjetaPersonaVulnerable,
                                   Heladera heladera,
                                   LocalDateTime fechaHora) {
    return RetiroDeVianda
        .builder()
        .tarjetaPersonaVulnerable(tarjetaPersonaVulnerable)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .build();
  }
}
