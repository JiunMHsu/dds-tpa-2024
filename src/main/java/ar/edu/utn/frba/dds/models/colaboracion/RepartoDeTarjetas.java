package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.tarjeta.TarjetaPersonaVulnerable;
import java.time.LocalDateTime;
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

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reparto_tarjetas")
public class RepartoDeTarjetas {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "colaborador_id", nullable = false)
  private Colaborador colaborador;

  @Column(name = "fecha_hora", columnDefinition = "DATETIME", nullable = false)
  private LocalDateTime fechaHora;

  // TODO - Completar mapeo
  private TarjetaPersonaVulnerable tarjeta;
  private PersonaVulnerable personaVulnerable;

  public static RepartoDeTarjetas por(Colaborador colaborador,
                                      LocalDateTime fechaHoraReparto,
                                      TarjetaPersonaVulnerable tarjeta,
                                      PersonaVulnerable personaVulnerable) {
    return RepartoDeTarjetas
        .builder()
        .colaborador(colaborador)
        .fechaHora(fechaHoraReparto)
        .tarjeta(tarjeta)
        .personaVulnerable(personaVulnerable)
        .build();
  }

  public static RepartoDeTarjetas por(Colaborador colaborador,
                                      LocalDateTime fechaHoraReparto) {
    return RepartoDeTarjetas
        .builder()
        .colaborador(colaborador)
        .fechaHora(fechaHoraReparto)
        .build();
  }

}
