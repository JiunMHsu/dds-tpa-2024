package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.tarjeta.TarjetaColaborador;
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
@Table(name = "apertura_heladera")
public class AperturaHeladera {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "tarjeta_id", nullable = false)
  private TarjetaColaborador tarjetaColaborador;

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  @Column(name = "fecha_hora")
  private LocalDateTime fechaHora;

  public static AperturaHeladera por(TarjetaColaborador tarjetaColaborador,
                                     Heladera heladera,
                                     LocalDateTime fechaHora) {
    return AperturaHeladera
        .builder()
        .tarjetaColaborador(tarjetaColaborador)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .build();
  }
}
