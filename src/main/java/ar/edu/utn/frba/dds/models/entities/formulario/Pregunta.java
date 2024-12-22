package ar.edu.utn.frba.dds.models.entities.formulario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pregunta")
public class Pregunta {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "contenido", columnDefinition = "TEXT", nullable = false)
  private String contenido;

}