package ar.edu.utn.frba.dds.models.formulario;

import java.util.List;
import lombok.Getter;

@Getter
public class Formulario {
  private final List<Pregunta> preguntas;

  public Formulario(List<Pregunta> preguntas) {
    this.preguntas = preguntas;
  }

  public void agregarCampo(Pregunta pregunta) {
    preguntas.add(pregunta);
  }

  public void quitarCampo(Pregunta pregunta) {
    preguntas.remove(pregunta);
  }
}