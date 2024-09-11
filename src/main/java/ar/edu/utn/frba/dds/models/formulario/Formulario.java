package ar.edu.utn.frba.dds.models.formulario;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "formulario")
public class Formulario {

  @Id
  @GeneratedValue
  private Long id;

  @OneToMany
  @JoinColumn(name = "formulario_id")
  private List<Pregunta> preguntas;

  public Formulario(List<Pregunta> preguntas) {
    this.preguntas = preguntas;
  }

  public Formulario() {
    preguntas = null;
  }

  public void agregarCampo(Pregunta pregunta) {
    preguntas.add(pregunta);
  }

  public void quitarCampo(Pregunta pregunta) {
    preguntas.remove(pregunta);
  }
}