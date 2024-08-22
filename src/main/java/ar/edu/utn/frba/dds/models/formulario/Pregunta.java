package ar.edu.utn.frba.dds.models.formulario;

import lombok.Getter;

@Getter
public class Pregunta {
  private final String contenido;

  public Pregunta(String pregunta) {
    this.contenido = pregunta;
  }
}