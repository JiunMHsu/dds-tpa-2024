package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.vianda.Vianda;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donacion_vianda")
public class DonacionVianda extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @Column(name = "fecha_hora", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime fechaHora;

    @OneToOne
    @JoinColumn(name = "vianda_id", nullable = false)
    private Vianda vianda;

    @Setter
    @Column(name = "es_entregada", nullable = false)
    private Boolean esEntregada;

    public static DonacionVianda por(Colaborador colaborador,
                                     LocalDateTime fechaDonacion,
                                     Vianda vianda,
                                     Boolean esEntregada) {
        return DonacionVianda
                .builder()
                .colaborador(colaborador)
                .fechaHora(fechaDonacion)
                .vianda(vianda)
                .esEntregada(esEntregada)
                .build();
    }

    public static DonacionVianda por(Colaborador colaborador,
                                     LocalDateTime fechaDonacion) {
        return DonacionVianda
                .builder()
                .colaborador(colaborador)
                .fechaHora(fechaDonacion)
                .build();
    }

}