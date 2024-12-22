package ar.edu.utn.frba.dds.models.entities.formulario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "respuesta")
public class Respuesta {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne
  @JoinColumn(name = "pregunta_id", nullable = false)
  private Pregunta pregunta;

  @Column(name = "descripcion", columnDefinition = "TEXT")
  private String descripcion;

}