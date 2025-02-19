package ar.edu.utn.frba.dds.models.entities.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.Period;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Registro de la periociadad de una donación de dinero. Configurada por un {@link Colaborador}.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donacion_dinero_periodica")
public class DonacionDineroPeriodica extends EntidadPersistente {

  @OneToOne
  @JoinColumn(name = "colaborador_id", nullable = false, unique = true)
  private Colaborador colaborador;

  @Column(name = "monto", nullable = false)
  private Integer monto;

  @Setter
  @Column(name = "frecuencia", columnDefinition = "VARCHAR(50)")
  private Period frecuencia;

  /**
   * Crea una donación de dinero periódica.
   *
   * @param colaborador {@link Colaborador} que dona el dinero
   * @param monto       monto donado
   * @param frecuencia  frecuencia de la donación
   * @return donación de dinero periódica
   */
  public static DonacionDineroPeriodica por(Colaborador colaborador,
                                            Integer monto,
                                            Period frecuencia) {
    return DonacionDineroPeriodica.builder()
        .colaborador(colaborador)
        .monto(monto)
        .frecuencia(frecuencia)
        .build();
  }
}
