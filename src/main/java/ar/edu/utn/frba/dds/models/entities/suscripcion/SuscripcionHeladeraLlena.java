package ar.edu.utn.frba.dds.models.entities.suscripcion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.util.Optional;
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
import lombok.Setter;

/**
 * Modelo Suscripción por heladera llena.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suscripcion_heladera_llena")
public class SuscripcionHeladeraLlena extends EntidadPersistente {

  @ManyToOne
  @JoinColumn(name = "colaborador_id", nullable = false)
  private Colaborador colaborador;

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  @Enumerated(EnumType.STRING)
  @Column(name = "medio_notificacion", nullable = false)
  private MedioDeNotificacion medioDeNotificacion;

  @Setter
  @Column(name = "umbral_espacio", nullable = false)
  private Integer umbralEspacio;

  /**
   * Crea una suscripción por heladera llena.
   *
   * @param colaborador         Colaborador.
   * @param heladera            Heladera.
   * @param medioDeNotificacion Medio de notificación.
   * @param espacioRestante     Espacio restante.
   * @return Suscripción por heladera llena.
   */
  public static SuscripcionHeladeraLlena de(Colaborador colaborador,
                                            Heladera heladera,
                                            MedioDeNotificacion medioDeNotificacion,
                                            Integer espacioRestante) {
    return SuscripcionHeladeraLlena
        .builder()
        .colaborador(colaborador)
        .heladera(heladera)
        .medioDeNotificacion(medioDeNotificacion)
        .umbralEspacio(espacioRestante)
        .build();
  }

  /**
   * Devuelve el contacto a notificar.
   *
   * @return contacto a notificar
   */
  public Optional<Contacto> contactoNotificar() {
    return colaborador.getContacto(this.medioDeNotificacion);
  }

  /**
   * Indica si se debe notificar.
   *
   * @param espacioRestante Espacio restante.
   * @return true si se debe notificar, false en caso contrario.
   */
  public boolean debeSerNotificado(int espacioRestante) {
    return espacioRestante <= this.umbralEspacio;
  }
}
