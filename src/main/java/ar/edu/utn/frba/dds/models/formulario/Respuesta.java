package ar.edu.utn.frba.dds.models.formulario;

import lombok.Getter;

import javax.persistence.*;

@Getter
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

    public Respuesta(Pregunta pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.descripcion = respuesta;
    }

    public Respuesta() {
    }
}