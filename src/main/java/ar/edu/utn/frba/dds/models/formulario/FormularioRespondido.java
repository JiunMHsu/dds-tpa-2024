package ar.edu.utn.frba.dds.models.formulario;

import java.util.ArrayList;
import lombok.Getter;

@Getter
public class FormularioRespondido {
  private ArrayList<Respuesta> respuestas;
  private Formulario formulario;

  public FormularioRespondido(Formulario formulario, ArrayList<Respuesta> respuestas) {
    this.formulario = formulario;
    this.respuestas = respuestas;
  }
}