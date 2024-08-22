package ar.edu.utn.frba.dds.models.formulario;

import lombok.Getter;

@Getter
public class Respuesta {
  private final Pregunta pregunta;
  private final String respuesta;

  public Respuesta(Pregunta pregunta, String respuesta) {
    this.pregunta = pregunta;
    this.respuesta = respuesta;
  }
}