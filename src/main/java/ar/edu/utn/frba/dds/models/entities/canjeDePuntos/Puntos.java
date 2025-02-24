package ar.edu.utn.frba.dds.models.entities.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.colaborador.TipoColaborador;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa los puntos de un colaborador.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Puntos {

  @Getter
  @Column(name = "puntos")
  private double puntos;

  @Setter
  @Column(name = "es_valido")
  private boolean esValido;

  @Column(name = "valido_hasta", columnDefinition = "DATE")
  private LocalDate validoHasta;

  /**
   * Verifica si los puntos son válidos según el tipo de colaborador.
   *
   * @param tipoColaborador {@link TipoColaborador} del colaborador.
   * @return Puntos.
   */
  public boolean esValidoSegun(TipoColaborador tipoColaborador) {
    return switch (tipoColaborador) {
      case HUMANO -> this.esValido;
      case JURIDICO -> this.esValido && this.validoHasta.isAfter(LocalDate.now());
    };
  }

  /**
   * Builder puntos iniciales.
   */
  public static Puntos iniciales() {
    return Puntos.builder()
        .puntos(0)
        .esValido(true)
        .validoHasta(LocalDate.now().plusMonths(1))
        .build();
  }
}
