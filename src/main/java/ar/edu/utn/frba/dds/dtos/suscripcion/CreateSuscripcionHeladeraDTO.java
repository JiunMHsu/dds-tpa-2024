package ar.edu.utn.frba.dds.dtos.suscripcion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para crear una suscripción a una heladera.
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@Getter
@Setter
@AllArgsConstructor
public class CreateSuscripcionHeladeraDTO {
  private Colaborador colaborador;
  private Heladera heladera;
  private MedioDeNotificacion medioDeNotificacion;
  private String infoContacto;
  private Integer espacioRestante;
  private Integer viandasRestantes;

  /**
   * Constructor para crear una suscripción a una heladera.
   *
   * @param colaborador         El colaborador que se suscribe.
   * @param heladera            La heladera a la que se suscribe.
   * @param medioDeNotificacion El medio de notificación para la suscripción.
   * @param infoContacto        La información de contacto para la suscripción.
   */
  public CreateSuscripcionHeladeraDTO(Colaborador colaborador,
                                      Heladera heladera,
                                      MedioDeNotificacion medioDeNotificacion,
                                      String infoContacto) {
    this.colaborador = colaborador;
    this.heladera = heladera;
    this.medioDeNotificacion = medioDeNotificacion;
    this.infoContacto = infoContacto;
    this.espacioRestante = null;
    this.viandasRestantes = null;
  }

}
