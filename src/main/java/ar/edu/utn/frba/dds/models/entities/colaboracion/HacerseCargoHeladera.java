package ar.edu.utn.frba.dds.models.entities.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
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
@Table(name = "hacerse_cargo_heladera")
public class HacerseCargoHeladera extends EntidadPersistente {

    // Un colaborador puede ser encargado de multiples heladeras
    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @Column(name = "fecha_hora", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime fechaHora;

    // Las heladeras pueden pasar de dueño en dueño
    @ManyToOne
    @JoinColumn(name = "heladera_a_cargo_id", nullable = false)
    private Heladera heladeraACargo;

    public static HacerseCargoHeladera por(Colaborador colaborador,
                                           LocalDateTime fechaHora,
                                           Heladera heladeraACargo) {
        return HacerseCargoHeladera
                .builder()
                .colaborador(colaborador)
                .fechaHora(fechaHora)
                .heladeraACargo(heladeraACargo)
                .build();
    }

    public static HacerseCargoHeladera por(Colaborador colaborador,
                                           Heladera heladeraACargo) {
        return HacerseCargoHeladera
                .builder()
                .colaborador(colaborador)
                .heladeraACargo(heladeraACargo)
                .build();
    }

}