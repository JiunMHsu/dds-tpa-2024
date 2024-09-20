package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
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
@Table(name = "reparto_tarjetas")
public class RepartoDeTarjetas extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @Column(name = "fecha_hora", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime fechaHora;

    @OneToOne
    @JoinColumn(name = "tarjeta_vulnerable_id", nullable = false)
    private TarjetaPersonaVulnerable tarjeta;

    @OneToOne
    @JoinColumn(name = "persona_vulnerable_id", nullable = false)
    private PersonaVulnerable personaVulnerable;

    public static RepartoDeTarjetas por(Colaborador colaborador,
                                        LocalDateTime fechaHoraReparto,
                                        TarjetaPersonaVulnerable tarjeta,
                                        PersonaVulnerable personaVulnerable) {
        return RepartoDeTarjetas
                .builder()
                .colaborador(colaborador)
                .fechaHora(fechaHoraReparto)
                .tarjeta(tarjeta)
                .personaVulnerable(personaVulnerable)
                .build();
    }

    public static RepartoDeTarjetas por(Colaborador colaborador,
                                        LocalDateTime fechaHoraReparto) {
        return RepartoDeTarjetas
                .builder()
                .colaborador(colaborador)
                .fechaHora(fechaHoraReparto)
                .build();
    }

}
