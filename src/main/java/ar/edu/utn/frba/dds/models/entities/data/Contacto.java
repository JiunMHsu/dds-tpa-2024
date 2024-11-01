package ar.edu.utn.frba.dds.models.entities.data;

import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Contacto {

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "whatsapp")
    private String whatsApp; // Chat ID

    @Column(name = "telegram")
    private String telegram; // Chat ID

    public static Contacto con(String email, String telefono, String whatsApp, String telegram) {
        return Contacto
                .builder()
                .email(email)
                .telefono(telefono)
                .whatsApp(whatsApp)
                .telegram(telegram)
                .build();
    }

    public static Contacto conTelegram(String telegram) {
        return Contacto.con(null, null, null, telegram);
    }

    public static Contacto conWhatsApp(String whatsApp) {
        return Contacto.con(null, null, whatsApp, null);
    }

    public static Contacto conEmail(String email) {
        return Contacto.con(email, null, null, null);
    }

    public static Contacto vacio() {
        return Contacto.con(null, null, null, null);
    }

    public String getContacto(MedioDeNotificacion medioDeNotificacion) {
        return switch (medioDeNotificacion) {
            case WHATSAPP -> this.getWhatsApp();
            case TELEGRAM -> this.getTelegram();
            case EMAIL -> this.getEmail();
        };
    }
}