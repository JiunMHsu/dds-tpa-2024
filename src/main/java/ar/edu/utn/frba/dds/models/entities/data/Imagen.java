package ar.edu.utn.frba.dds.models.entities.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Representa una imagen.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Imagen {

  @Column(name = "ruta", columnDefinition = "TEXT")
  private String ruta;
}
