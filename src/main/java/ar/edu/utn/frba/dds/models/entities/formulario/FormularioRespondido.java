package ar.edu.utn.frba.dds.models.entities.formulario;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "formulario_respondido")
public class FormularioRespondido {

  @Id
  @GeneratedValue
  private Long id;

  @OneToMany
  @JoinColumn(name = "formulario_respondido_id")
  private List<Respuesta> respuestas;

  @ManyToOne
  @JoinColumn(name = "formulario_id")
  private Formulario formulario;

}
