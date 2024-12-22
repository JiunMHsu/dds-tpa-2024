package ar.edu.utn.frba.dds.models.entities.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.colaborador.TipoColaborador;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  public boolean esValido(TipoColaborador tipoColaborador) {
    return switch (tipoColaborador) {
      case HUMANO -> esValido;
      case JURIDICO -> esValido && validoHasta.isAfter(LocalDate.now());
    };
  }
}
