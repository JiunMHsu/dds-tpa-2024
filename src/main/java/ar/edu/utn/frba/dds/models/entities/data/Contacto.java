package ar.edu.utn.frba.dds.models.entities.data;

import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo Contacto.
 */
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

  /**
   * Crea un contacto con un medio de notificación y un valor.
   *
   * @param medioDeNotificacion Medio de notificación.
   * @param valor               Valor.
   * @return Contacto.
   */
  public static Contacto con(MedioDeNotificacion medioDeNotificacion, String valor) {
    return Contacto
        .builder()
        .medioDeNotificacion(medioDeNotificacion)
        .valor(valor)
        .build();
  }

  /**
   * Crea un contacto con un medio de notificación de Telegram.
   *
   * @param telegram chatId del chat Telegram
   * @return Contacto.
   */
  public static Contacto conTelegram(String telegram) {
    return Contacto.con(MedioDeNotificacion.TELEGRAM, telegram);
  }

  /**
   * Crea un contacto con un medio de notificación de WhatsApp.
   *
   * @param whatsApp numero asociado a WhatsApp
   * @return Contacto.
   */
  public static Contacto conWhatsApp(String whatsApp) {
    return Contacto.con(MedioDeNotificacion.WHATSAPP, whatsApp);
  }

  /**
   * Crea un contacto con un medio de notificación de Email.
   *
   * @param email direccion de correo electronico
   * @return Contacto.
   */
  public static Contacto conEmail(String email) {
    return Contacto.con(MedioDeNotificacion.EMAIL, email);
  }

  /**
   * Crea un contacto con un medio de notificación de Teléfono.
   *
   * @param telefono numero de telefono
   * @return Contacto.
   */
  public static Contacto conTelefono(String telefono) {
    return Contacto.con(MedioDeNotificacion.TELEFONO, telefono);
  }

  /**
   * Crea un contacto vacío.
   *
   * @return Contacto.
   */
  public static Contacto vacio() {
    return Contacto.con(null, null);
  }
}