package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Period;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donacion_dinero")
public class DonacionDinero extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @Column(name = "fecha_hora", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "monto", nullable = false)
    private Integer monto;

    // TODO - Ver como persistir Period
    // Podria realizarse una conversion del dato a string??
    // existe la funcion PERIOD_ADD() pero se maneja en meses nada mas, no estoy seguro de que sirva

    @Setter
    @Column(name = "frecuencia")
    private Period frecuencia;

    public static DonacionDinero por(Colaborador colaborador,
                                     LocalDateTime fechaDonacion,
                                     Integer monto,
                                     Period frecuencia) {
        return DonacionDinero
                .builder()
                .colaborador(colaborador)
                .fechaHora(fechaDonacion)
                .monto(monto)
                .frecuencia(frecuencia)
                .build();
    }

    public static DonacionDinero por(Colaborador colaborador,
                                     LocalDateTime fechaDonacion,
                                     Integer monto) {
        return DonacionDinero
                .builder()
                .colaborador(colaborador)
                .fechaHora(fechaDonacion)
                .monto(monto)
                .build();
    }

}