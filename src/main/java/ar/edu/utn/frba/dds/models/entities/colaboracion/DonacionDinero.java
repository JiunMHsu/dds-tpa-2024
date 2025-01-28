package ar.edu.utn.frba.dds.models.entities.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
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

/**
 * Colaboración de un {@link Colaborador} que dona dinero.
 */
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

  /**
   * Crea una donación de dinero.
   *
   * @param colaborador {@link Colaborador} que dona el dinero
   * @param fechaHora   fecha y hora de la donación
   * @param monto       monto donado
   * @return donación de dinero
   */
  public static DonacionDinero por(Colaborador colaborador,
                                   LocalDateTime fechaHora,
                                   Integer monto) {
    return DonacionDinero.builder()
        .colaborador(colaborador)
        .fechaHora(fechaHora)
        .monto(monto)
        .build();
  }
}