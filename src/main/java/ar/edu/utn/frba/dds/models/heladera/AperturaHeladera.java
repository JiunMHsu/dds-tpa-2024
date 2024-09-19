package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
