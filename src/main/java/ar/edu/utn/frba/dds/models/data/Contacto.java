package ar.edu.utn.frba.dds.models.data;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    public static Contacto with(String email, String telefono, String whatsApp, String telegram) {
        return Contacto
                .builder()
                .email(email)
                .telefono(telefono)
                .whatsApp(whatsApp)
                .telegram(telegram)
                .build();
    }

    public static Contacto ofTelegram(String telegram) {
        return Contacto
                .builder()
                .telegram(telegram)
                .build();
    }

    public static Contacto ofWhatsApp(String whatsApp) {
        return Contacto
                .builder()
                .whatsApp(whatsApp)
                .build();
    }

    public static Contacto empty() {
        return Contacto
                .builder().build();
    }

    public String getContacto(MedioDeNotificacion medioDeNotificacion) {
        return switch (medioDeNotificacion) {
            case WHATSAPP -> this.getWhatsApp();
            case TELEGRAM -> this.getTelegram();
            case EMAIL -> this.getEmail();
        };
    }
}