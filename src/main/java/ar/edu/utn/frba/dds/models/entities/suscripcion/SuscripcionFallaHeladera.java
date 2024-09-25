package ar.edu.utn.frba.dds.models.entities.suscripcion;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
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
@Table(name = "suscripcion_falla_heladera")
public class SuscripcionFallaHeladera extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;

    @Enumerated(EnumType.STRING)
    @Column(name = "medio_notificacion", nullable = false)
    private MedioDeNotificacion medioDeNotificacion;

    public static SuscripcionFallaHeladera de(Colaborador colaborador,
                                              Heladera heladera,
                                              MedioDeNotificacion medioDeNotificacion) {
        return SuscripcionFallaHeladera
                .builder()
                .colaborador(colaborador)
                .heladera(heladera)
                .medioDeNotificacion(medioDeNotificacion)
                .build();
    }

}
