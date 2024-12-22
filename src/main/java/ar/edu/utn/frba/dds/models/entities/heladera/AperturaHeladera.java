package ar.edu.utn.frba.dds.models.entities.heladera;

import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = "apertura_heladera")
public class AperturaHeladera extends EntidadPersistente {

  @ManyToOne
  @JoinColumn(name = "tarjeta_id", nullable = false)
  private TarjetaColaborador tarjetaColaborador;

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  @Column(name = "fecha_hora")
  private LocalDateTime fechaHora;

  @OneToOne
  @JoinColumn(name = "solicitud_apertura_id", referencedColumnName = "id")
  private SolicitudDeApertura solicitudDeApertura;

  public static AperturaHeladera por(TarjetaColaborador tarjetaColaborador,
                                     Heladera heladera,
                                     LocalDateTime fechaHora,
                                     SolicitudDeApertura solicitudDeApertura) {
    return AperturaHeladera
        .builder()
        .tarjetaColaborador(tarjetaColaborador)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .solicitudDeApertura(solicitudDeApertura)
        .build();
  }

}
