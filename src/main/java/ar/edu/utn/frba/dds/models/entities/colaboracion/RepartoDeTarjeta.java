package ar.edu.utn.frba.dds.models.entities.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
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

/**
 * Colaboración de un {@link Colaborador}
 * que reparte una {@link TarjetaPersonaVulnerable} a una {@link PersonaVulnerable}.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reparto_tarjetas")
public class RepartoDeTarjeta extends EntidadPersistente {

  @ManyToOne
  @JoinColumn(name = "colaborador_id", nullable = false)
  private Colaborador colaborador;

  @Column(name = "fecha_hora", columnDefinition = "DATETIME", nullable = false)
  private LocalDateTime fechaHora;

  @OneToOne
  @JoinColumn(name = "tarjeta_vulnerable_id") // nullable nueva compatibilidad
  private TarjetaPersonaVulnerable tarjeta;

  @OneToOne
  @JoinColumn(name = "persona_vulnerable_id") // nullable nueva compatibilidad
  private PersonaVulnerable personaVulnerable;

  /**
   * Crea un reparto de tarjetas.
   *
   * @param colaborador       {@link Colaborador} que reparte la tarjeta
   * @param fechaHora         fecha y hora del reparto
   * @param tarjeta           {@link TarjetaPersonaVulnerable} repartida
   * @param personaVulnerable {@link PersonaVulnerable} a la que se le reparte la tarjeta
   * @return un reparto de tarjetas
   */
  public static RepartoDeTarjeta por(Colaborador colaborador,
                                     LocalDateTime fechaHora,
                                     TarjetaPersonaVulnerable tarjeta,
                                     PersonaVulnerable personaVulnerable) {
    return RepartoDeTarjeta
        .builder()
        .colaborador(colaborador)
        .fechaHora(fechaHora)
        .tarjeta(tarjeta)
        .personaVulnerable(personaVulnerable)
        .build();
  }

  /**
   * Crea un reparto de tarjetas sin especificar la tarjeta repartida ni la persona vulnerable.
   *
   * @param colaborador {@link Colaborador} que reparte la tarjeta
   * @param fechaHora   fecha y hora del reparto
   * @return un reparto de tarjetas
   */
  public static RepartoDeTarjeta por(Colaborador colaborador,
                                     LocalDateTime fechaHora) {
    return RepartoDeTarjeta.por(colaborador, fechaHora, null, null);
  }

}
