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
@Table(name = "solicitud_apertura")
public class SolicitudDeApertura {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "tarjeta_id", nullable = false)
  private TarjetaColaborador tarjeta;

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  @Column(name = "fecha_hora")
  private LocalDateTime fechaHora;

  @ManyToOne
  @JoinColumn(name = "motivo_id", nullable = false) // TODO - ver si va o no
  private MotivoDeApertura motivo;

  public static SolicitudDeApertura por(TarjetaColaborador tarjeta,
                                        Heladera heladera,
                                        LocalDateTime fechaHora,
                                        MotivoDeApertura motivo) {
    return SolicitudDeApertura
        .builder()
        .tarjeta(tarjeta)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .motivo(motivo)
        .build();
  }

  public static SolicitudDeApertura por(TarjetaColaborador tarjeta,
                                        Heladera heladera) {
    return SolicitudDeApertura
        .builder()
        .tarjeta(tarjeta)
        .heladera(heladera)
        .fechaHora(LocalDateTime.now())
        .build();
  }
}
