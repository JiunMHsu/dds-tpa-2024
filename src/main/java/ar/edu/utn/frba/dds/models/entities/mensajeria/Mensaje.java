package ar.edu.utn.frba.dds.models.entities.mensajeria;

import ar.edu.utn.frba.dds.models.entities.data.Contacto;
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
import lombok.Setter;

/**
 * Modelo Mensaje.
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mensaje")
public class Mensaje extends EntidadPersistente {

  @Column(name = "asunto", nullable = false)
  private String asunto;

  @Column(name = "cuerpo", nullable = false)
  private String cuerpo;

  @ManyToOne
  @JoinColumn(name = "contacto_id")
  private Contacto contacto;

  @Setter
  @Column(name = "fecha_envio", nullable = false)
  private LocalDateTime fechaEnvio;

  /**
   * Crea un mensaje.
   *
   * @param contacto   Contacto.
   * @param asunto     Asunto.
   * @param cuerpo     Cuerpo.
   * @param fechaEnvio Fecha de envío.
   * @return Mensaje.
   */
  public static Mensaje para(Contacto contacto,
                             String asunto,
                             String cuerpo,
                             LocalDateTime fechaEnvio) {
    return Mensaje
        .builder()
        .contacto(contacto)
        .asunto(asunto)
        .cuerpo(cuerpo)
        .fechaEnvio(fechaEnvio)
        .build();
  }

  /**
   * Crea un mensaje.
   *
   * @param receptor Receptor.
   * @param asunto   Asunto.
   * @param cuerpo   Cuerpo.
   * @return Mensaje.
   */
  public static Mensaje con(Contacto receptor,
                            String asunto,
                            String cuerpo) {
    return Mensaje.para(receptor, asunto, cuerpo, null);
  }

}