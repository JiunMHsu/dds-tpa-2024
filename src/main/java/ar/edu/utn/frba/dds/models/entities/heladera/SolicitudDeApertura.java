package ar.edu.utn.frba.dds.models.entities.heladera;

import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "solicitud_apertura")
public class SolicitudDeApertura extends EntidadPersistente {

  @ManyToOne
  @JoinColumn(name = "tarjeta_id", nullable = false)
  private TarjetaColaborador tarjeta;

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  @Column(name = "fecha_hora")
  private LocalDateTime fechaHora;

  @Column(name = "motivo", columnDefinition = "TEXT")
  private String motivo;

  public static SolicitudDeApertura por(TarjetaColaborador tarjeta,
                                        Heladera heladera,
                                        LocalDateTime fechaHora,
                                        String motivo) {
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
    return SolicitudDeApertura.por(
        tarjeta,
        heladera,
        LocalDateTime.now(),
        ""
    );
  }

}
