package ar.edu.utn.frba.dds.models.entities.formulario;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Modelo Formulario.
 */
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

  /**
   * Agrega un campo al formulario.
   *
   * @param pregunta una {@link Pregunta}.
   */
  public void agregarCampo(Pregunta pregunta) {
    preguntas.add(pregunta);
  }

  /**
   * Quita un campo del formulario.
   *
   * @param pregunta una {@link Pregunta}.
   */
  public void quitarCampo(Pregunta pregunta) {
    preguntas.remove(pregunta);
  }

}