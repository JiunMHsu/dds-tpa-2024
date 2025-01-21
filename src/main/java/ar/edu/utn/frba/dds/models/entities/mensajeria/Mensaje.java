package ar.edu.utn.frba.dds.models.entities.mensajeria;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  @JoinColumn(name = "contacto_id")
  private Contacto contacto;

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

  public List<Contacto> getContacto() {
    if (colaborador != null)
      return colaborador.getContactos();
    else
      return new ArrayList<>(Arrays.asList(tecnico.getContacto()));
  }
}