package ar.edu.utn.frba.dds.models.entities.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

  @Setter
  @Column(name = "frecuencia", columnDefinition = "VARCHAR(50)")
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
    return DonacionDinero.por(colaborador, fechaDonacion, monto, null);
  }

}