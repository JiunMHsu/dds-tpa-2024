package ar.edu.utn.frba.dds.models.formulario;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "pregunta")
public class Pregunta {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "contenido", columnDefinition = "TEXT", nullable = false)
    private String contenido;

    public Pregunta(String pregunta) {
        this.contenido = pregunta;
    }

    public Pregunta() {
    }
}