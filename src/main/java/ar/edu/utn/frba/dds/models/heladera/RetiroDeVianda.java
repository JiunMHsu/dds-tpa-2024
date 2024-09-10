package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.tarjeta.TarjetaPersonaVulnerable;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "retiro_vianda")
public class RetiroDeVianda {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "tarjeta_id", nullable = false)
  private TarjetaPersonaVulnerable tarjetaPersonaVulnerable;

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  @Column(name = "fecha_hora")
  private LocalDateTime fechaHora;

  public static RetiroDeVianda por(TarjetaPersonaVulnerable tarjetaPersonaVulnerable,
                                   Heladera heladera,
                                   LocalDateTime fechaHora) {
    return RetiroDeVianda
        .builder()
        .tarjetaPersonaVulnerable(tarjetaPersonaVulnerable)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .build();
  }
}
