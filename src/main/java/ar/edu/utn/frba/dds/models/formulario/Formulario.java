package ar.edu.utn.frba.dds.models.formulario;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "formulario")
public class Formulario {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    @JoinColumn(name = "formulario_id")
    private List<Pregunta> preguntas;

    public Formulario(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public Formulario() {
        preguntas = null;
    }

    public void agregarCampo(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    public void quitarCampo(Pregunta pregunta) {
        preguntas.remove(pregunta);
    }
}