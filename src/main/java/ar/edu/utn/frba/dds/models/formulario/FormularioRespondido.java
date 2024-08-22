package ar.edu.utn.frba.dds.models.formulario;

import java.util.List;
import lombok.Getter;

@Getter
public class FormularioRespondido {
  private final List<Respuesta> respuestas;
  private final Formulario formulario;

  public FormularioRespondido(Formulario formulario, List<Respuesta> respuestas) {
    this.formulario = formulario;
    this.respuestas = respuestas;
  }
}