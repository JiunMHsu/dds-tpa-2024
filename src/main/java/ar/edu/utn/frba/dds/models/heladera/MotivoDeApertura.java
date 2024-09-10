package ar.edu.utn.frba.dds.models.heladera;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Entity
@Table(name = "motivo_apertura")
public class MotivoDeApertura {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @Column(name = "descripcion")
  private final String descripcion;

  public MotivoDeApertura(String descripcion) {
    this.descripcion = descripcion;
  }

  public MotivoDeApertura() {
    this.descripcion = null;
  }
}
