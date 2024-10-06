package ar.edu.utn.frba.dds.models.entities.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.vianda.Vianda;
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
import lombok.Setter;

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
    @JoinColumn(name = "vianda_id") // nullable por compatibilidad
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
        return DonacionVianda.por(colaborador, fechaDonacion, null, true);
    }

}