package ar.edu.utn.frba.dds.models.incidente;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
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
@Table(name = "alerta")
public class Alerta implements Incidente {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @Enumerated
  private TipoIncidente tipo;

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  @Column (name = "fecha_hora")
  private LocalDateTime fechaHora;

  public static Alerta por(TipoIncidente tipo, Heladera heladera, LocalDateTime fechaHora) {
    return Alerta
        .builder()
        .tipo(tipo)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .build();
  }

}
