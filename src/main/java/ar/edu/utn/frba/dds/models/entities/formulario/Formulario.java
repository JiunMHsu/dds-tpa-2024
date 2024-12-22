package ar.edu.utn.frba.dds.models.entities.formulario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "formulario")
public class Formulario {

  @Id
  @GeneratedValue
  private Long id;

  @OneToMany
  @JoinColumn(name = "formulario_id")
  private List<Pregunta> preguntas;

  public void agregarCampo(Pregunta pregunta) {
    preguntas.add(pregunta);
  }

  public void quitarCampo(Pregunta pregunta) {
    preguntas.remove(pregunta);
  }

}