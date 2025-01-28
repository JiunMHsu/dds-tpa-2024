package ar.edu.utn.frba.dds.models.entities.tarjeta;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import ar.edu.utn.frba.dds.utils.RandomString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa una tarjeta de colaborador en el sistema.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tarjeta_colaborador")
public class TarjetaColaborador extends EntidadPersistente {

  @Column(name = "codigo", nullable = false)
  private String codigo;

  @ManyToOne
  @JoinColumn(name = "colaborador_id", nullable = false)
  private Colaborador duenio;

  @Setter
  @Column(name = "esta_activa")
  private Boolean estaActiva;

  /**
   * Crea una tarjeta de colaborador.
   *
   * @param codigo     Código de la tarjeta.
   * @param duenio     Dueño de la tarjeta.
   * @param estaActiva Indica si la tarjeta está activa.
   * @return Tarjeta de colaborador.
   */
  public static TarjetaColaborador de(String codigo,
                                      Colaborador duenio,
                                      Boolean estaActiva) {
    return TarjetaColaborador
        .builder()
        .codigo(codigo)
        .duenio(duenio)
        .estaActiva(estaActiva)
        .build();
  }

  /**
   * Crea una tarjeta de colaborador.
   *
   * @param duenio Dueño de la tarjeta.
   * @return Tarjeta de colaborador.
   */
  public static TarjetaColaborador de(Colaborador duenio) {
    return TarjetaColaborador.de(new RandomString(11).nextString(), duenio, true);
  }

}
