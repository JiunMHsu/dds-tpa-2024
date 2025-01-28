package ar.edu.utn.frba.dds.models.entities.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Representa un área geográfica.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Area {

  @Embedded
  private Ubicacion ubicacion;

  @Column(name = "radio")
  private Integer radio;

  @Column(name = "barrio")
  private Barrio barrio;

  /**
   * Constructor de la clase.
   *
   * @param ubicacion Ubicación del área.
   * @param radio     Radio del área.
   * @param barrio    Barrio del área.
   * @return Área creada.
   */
  public static Area con(Ubicacion ubicacion,
                         Integer radio,
                         Barrio barrio) {
    return Area
        .builder()
        .ubicacion(ubicacion)
        .radio(radio)
        .barrio(barrio)
        .build();
  }

  /**
   * Calcula la distancia entre dos ubicaciones.
   * TODO: Revisar si hace falta.
   *
   * @param unaUbicacion Ubicación a la que se le quiere calcular la distancia.
   * @return Distancia entre las ubicaciones.
   */
  public double distanciaA(Ubicacion unaUbicacion) {
    double distanciaEntreUbicaciones = this.ubicacion.distanciaA(unaUbicacion);
    return distanciaEntreUbicaciones - radio;
  }
}
