package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "donacion_dinero")
public class DonacionDinero {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

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