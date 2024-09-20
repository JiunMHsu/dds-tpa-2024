package ar.edu.utn.frba.dds.models.suscripcion;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "suscripcion_heladera_llena")
public class SuscripcionHeladeraLlena extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;

    @Enumerated(EnumType.STRING)
    @Column(name = "medio_notificacion", nullable = false)
    private MedioDeNotificacion medioDeNotificacion;

    @Column(name = "espacio_restante", nullable = false)
    private Integer espacioRestante;

    public static SuscripcionHeladeraLlena de(Colaborador colaborador,
                                              Heladera heladera,
                                              MedioDeNotificacion medioDeNotificacion,
                                              Integer espacioRestante) {
        return SuscripcionHeladeraLlena
                .builder()
                .colaborador(colaborador)
                .heladera(heladera)
                .medioDeNotificacion(medioDeNotificacion)
                .espacioRestante(espacioRestante)
                .build();
    }

    public Boolean debeSerNotificado(Integer espacioRestante) {
        return espacioRestante <= this.espacioRestante;
    }
}
