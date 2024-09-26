package ar.edu.utn.frba.dds.models.entities.heladera;

import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
