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
 * Modelo Suscripción por falta de vianda.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suscripcion_falta_vianda")
public class SuscripcionFaltaVianda extends EntidadPersistente {

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
  @Column(name = "umbral_viandas", nullable = false)
  private Integer umbralViandas;

  /**
   * Crea una suscripción por falta de vianda.
   *
   * @param colaborador         Colaborador.
   * @param heladera            Heladera.
   * @param medioDeNotificacion Medio de notificación.
   * @param viandasRestantes    Viandas restantes.
   * @return Suscripción por falta de vianda.
   */
  public static SuscripcionFaltaVianda de(Colaborador colaborador,
                                          Heladera heladera,
                                          MedioDeNotificacion medioDeNotificacion,
                                          Integer viandasRestantes) {
    return SuscripcionFaltaVianda
        .builder()
        .colaborador(colaborador)
        .heladera(heladera)
        .medioDeNotificacion(medioDeNotificacion)
        .umbralViandas(viandasRestantes)
        .build();
  }

  /**
   * Devuelve el contacto a notificar.
   *
   * @return contacto a notificar
   */
  public Optional<Contacto> contactoANotificar() {
    return colaborador.getContacto(this.medioDeNotificacion);
  }

  /**
   * Indica si se debe notificar.
   *
   * @param viandasRestantes Viandas restantes.
   * @return true si se debe notificar, false en caso contrario.
   */
  public boolean debeSerNotificado(int viandasRestantes) {
    return viandasRestantes <= this.umbralViandas;
  }

}
