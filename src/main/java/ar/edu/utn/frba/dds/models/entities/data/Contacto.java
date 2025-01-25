package ar.edu.utn.frba.dds.models.entities.data;

import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacto")
public class Contacto extends EntidadPersistente {

  @Column(name = "medio_notificacion")
  private MedioDeNotificacion medioDeNotificacion;

  @Column(name = "valor")
  private String valor;

  public static Contacto con(MedioDeNotificacion medioDeNotificacion, String valor) {
    return Contacto
        .builder()
        .medioDeNotificacion(medioDeNotificacion)
        .valor(valor)
        .build();
  }

  public static Contacto conTelegram(String telegram) {
    return Contacto.con(MedioDeNotificacion.TELEGRAM, telegram);
  }

  public static Contacto conWhatsApp(String whatsApp) {
    return Contacto.con(MedioDeNotificacion.WHATSAPP, whatsApp);
  }

  public static Contacto conEmail(String email) {
    return Contacto.con(MedioDeNotificacion.EMAIL, email);
  }

  public static Contacto vacio() {
    return Contacto.con(null, null);
  }
}