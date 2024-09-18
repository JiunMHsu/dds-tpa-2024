package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.tarjeta.TarjetaColaborador;
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

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    // @ManyToOne
    // @JoinColumn(name = "motivo_id", nullable = false)
    // private MotivoDeApertura motivo;

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
        return SolicitudDeApertura
                .builder()
                .tarjeta(tarjeta)
                .heladera(heladera)
                .fechaHora(LocalDateTime.now())
                .build();
    }
}
