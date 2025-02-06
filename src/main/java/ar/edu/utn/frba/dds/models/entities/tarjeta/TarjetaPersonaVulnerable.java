package ar.edu.utn.frba.dds.models.entities.tarjeta;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import ar.edu.utn.frba.dds.utils.RandomString;
import java.time.LocalDate;
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
 * Representa la tarjeta de una {@link PersonaVulnerable}.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tarjeta_persona_vulnerable")
public class TarjetaPersonaVulnerable extends EntidadPersistente {

  @Column(name = "codigo", nullable = false)
  private String codigo;

  @OneToOne
  @JoinColumn(name = "persona_vulnerable_id", unique = true, nullable = false)
  private PersonaVulnerable duenio;

  @Setter
  @Column(name = "usos_del_dia")
  private Integer usosEnElDia;

  @Setter
  @Column(name = "fecha_ultimo_uso", columnDefinition = "DATE")
  private LocalDate ultimoUso;

  /**
   * Crea una tarjeta de persona vulnerable.
   *
   * @param codigo      Código de la tarjeta.
   * @param duenio      Dueño de la tarjeta.
   * @param usosEnElDia Cantidad de usos en el día.
   * @param ultimoUso   Fecha del último uso.
   * @return Tarjeta de persona vulnerable.
   */
  public static TarjetaPersonaVulnerable de(String codigo,
                                            PersonaVulnerable duenio,
                                            int usosEnElDia,
                                            LocalDate ultimoUso) {
    return TarjetaPersonaVulnerable
        .builder()
        .codigo(codigo)
        .duenio(duenio)
        .usosEnElDia(usosEnElDia)
        .ultimoUso(ultimoUso)
        .build();
  }

  /**
   * Crea una tarjeta de persona vulnerable.
   *
   * @param codigo Código de la tarjeta.
   * @param duenio Dueño de la tarjeta.
   * @return Tarjeta de persona vulnerable.
   */
  public static TarjetaPersonaVulnerable de(String codigo,
                                            PersonaVulnerable duenio) {
    return TarjetaPersonaVulnerable.de(
        codigo,
        duenio,
        0,
        LocalDate.now());
  }

  /**
   * Crea una tarjeta de persona vulnerable.
   *
   * @param duenio Dueño de la tarjeta.
   * @return Tarjeta de persona vulnerable.
   */
  public static TarjetaPersonaVulnerable de(PersonaVulnerable duenio) {
    return TarjetaPersonaVulnerable.de(
        new RandomString(11).generate(),
        duenio,
        0,
        LocalDate.now());
  }

  /**
   * Crea una tarjeta de persona vulnerable.
   *
   * @return Tarjeta de persona vulnerable.
   */
  public static TarjetaPersonaVulnerable de() {
    return TarjetaPersonaVulnerable.de(null);
  }

  private boolean puedeUsar() {
    if (LocalDate.now().isAfter(ultimoUso)) {
      this.setUsosEnElDia(0);
    }
    return usosEnElDia < this.usosPorDia();
  }

  /**
   * Indica si la tarjeta puede ser utilizada en una {@link Heladera}.
   *
   * @param heladera Heladera.
   * @return {@code true} si puede ser utilizada, {@code false} en caso contrario.
   */
  public boolean puedeUsarseEn(Heladera heladera) {
    return heladera.estaActiva() && this.puedeUsar();
  }

  /**
   * Indica la cantidad de usos permitidos por día.
   *
   * @return Cantidad de usos permitidos por día.
   */
  public Integer usosPorDia() {
    return 4 + duenio.getMenoresACargo() * 2;
  }

  /**
   * Suma un uso a la tarjeta.
   */
  public void sumarUso() {
    usosEnElDia += 1;
  }
}
