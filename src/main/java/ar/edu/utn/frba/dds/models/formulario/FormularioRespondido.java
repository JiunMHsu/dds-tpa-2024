package ar.edu.utn.frba.dds.models.formulario;

import java.util.ArrayList;
import lombok.Getter;

@Getter
public class FormularioRespondido {
  private ArrayList<Respuesta> respuestas;
  private Formulario formulario;

  public FormularioRespondido(Formulario formulario) {
    this.formulario = formulario;
    this.respuestas = new ArrayList<>();
  }

  public void responderFormulario(ArrayList<Respuesta> respuestas) {
    this.respuestas.addAll(respuestas);
  }
}