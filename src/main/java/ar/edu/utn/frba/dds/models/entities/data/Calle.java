package ar.edu.utn.frba.dds.models.entities.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Calle {
  @Column(name = "calle_nombre")
  private String nombre;

}
