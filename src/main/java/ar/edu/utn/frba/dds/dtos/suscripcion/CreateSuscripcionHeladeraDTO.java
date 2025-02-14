package ar.edu.utn.frba.dds.dtos.suscripcion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateSuscripcionHeladeraDTO {
  private  Colaborador colaborador;
  private  Heladera heladera;
  private  MedioDeNotificacion medioDeNotificacion;
  private String infoContacto;
  private  Integer espacioRestante;
  private  Integer viandasRestantes;

  public CreateSuscripcionHeladeraDTO(Colaborador colaborador, Heladera heladera, MedioDeNotificacion medioDeNotificacion, String infoContacto) {
    this.colaborador = colaborador;
    this.heladera = heladera;
    this.medioDeNotificacion = medioDeNotificacion;
    this.infoContacto = infoContacto;
    this.espacioRestante = null;
    this.viandasRestantes = null;
  }

}
