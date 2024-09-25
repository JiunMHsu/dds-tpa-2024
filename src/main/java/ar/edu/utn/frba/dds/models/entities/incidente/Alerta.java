package ar.edu.utn.frba.dds.models.entities.incidente;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;

//@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("alerta")
public class Alerta extends Incidente {
}
