package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.tarjeta.TarjetaPersonaVulnerable;
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
@Table(name = "retiro_vianda")
public class RetiroDeVianda extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "tarjeta_id", nullable = false)
    private TarjetaPersonaVulnerable tarjetaPersonaVulnerable;

    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;

    @Column(name = "fecha_hora", columnDefinition = "DATETIME")
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
