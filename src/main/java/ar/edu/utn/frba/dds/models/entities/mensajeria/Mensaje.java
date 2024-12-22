package ar.edu.utn.frba.dds.models.entities.mensajeria;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mensaje")
public class Mensaje {

  @Id
  @GeneratedValue(generator = "uuid")
  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(name = "asunto", nullable = false)
  private String asunto;

  @Column(name = "cuerpo", nullable = false)
  private String cuerpo;

  @ManyToOne
  @JoinColumn(name = "colaborador_id")
  private Colaborador colaborador;

  @ManyToOne
  @JoinColumn(name = "tecnico_id")
  private Tecnico tecnico;

  @Setter
  @Enumerated
  @Column(name = "medio_notificacion", nullable = false)
  private MedioDeNotificacion medio;

  @Setter
  @Column(name = "fecha_envio", nullable = false)
  private LocalDateTime fechaEnvio;

  public static Mensaje para(Colaborador colaborador,
                             Tecnico tecnico,
                             String asunto,
                             String cuerpo,
                             MedioDeNotificacion medio,
                             LocalDateTime fechaEnvio) {
    return Mensaje
        .builder()
        .asunto(asunto)
        .cuerpo(cuerpo)
        .colaborador(colaborador)
        .tecnico(tecnico)
        .medio(medio)
        .fechaEnvio(fechaEnvio)
        .build();
  }

  public static Mensaje paraColaborador(Colaborador receptor,
                                        String asunto,
                                        String cuerpo) {
    return Mensaje.para(receptor, null, asunto, cuerpo, null, null);
  }

  public static Mensaje paraTecnico(Tecnico receptor,
                                    String asunto,
                                    String cuerpo) {
    return Mensaje.para(null, receptor, asunto, cuerpo, null, null);
  }

  public Contacto getContacto() {
    if (colaborador != null)
      return colaborador.getContacto();
    else
      return tecnico.getContacto();
  }
}