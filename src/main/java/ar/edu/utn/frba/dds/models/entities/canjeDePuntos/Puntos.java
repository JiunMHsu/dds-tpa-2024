package ar.edu.utn.frba.dds.models.entities.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.colaborador.TipoColaborador;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa los puntos de un colaborador.
 */
@NoArgsConstructor
@AllArgsConstructor
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
   * Constructor de la clase Puntos.
   *
   * @param tipoColaborador {@link TipoColaborador} del colaborador.
   * @return Puntos.
   */
  public boolean esValido(TipoColaborador tipoColaborador) {
    return switch (tipoColaborador) {
      case HUMANO -> esValido;
      case JURIDICO -> esValido && validoHasta.isAfter(LocalDate.now());
    };
  }
}
