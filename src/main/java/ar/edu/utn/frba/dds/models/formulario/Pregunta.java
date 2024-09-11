package ar.edu.utn.frba.dds.models.formulario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "pregunta")
public class Pregunta {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "contenido", columnDefinition = "TEXT", nullable = false)
  private String contenido;

  public Pregunta(String pregunta) {
    this.contenido = pregunta;
  }

  public Pregunta() {
  }
}