package ar.edu.utn.frba.dds.models.formulario;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "formulario_respondido")
public class FormularioRespondido {

  @Id
  @GeneratedValue
  private long id;

  private List<Respuesta> respuestas;

  private Formulario formulario;

  public FormularioRespondido(Formulario formulario, List<Respuesta> respuestas) {
    this.formulario = formulario;
    this.respuestas = respuestas;
  }

  public FormularioRespondido() {
    this.respuestas = null;
    this.formulario = null;
  }
}