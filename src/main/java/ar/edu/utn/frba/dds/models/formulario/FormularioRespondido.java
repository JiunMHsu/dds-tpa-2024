package ar.edu.utn.frba.dds.models.formulario;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
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

    public FormularioRespondido(Formulario formulario, List<Respuesta> respuestas) {
        this.formulario = formulario;
        this.respuestas = respuestas;
    }

    public FormularioRespondido() {
        this.respuestas = null;
        this.formulario = null;
    }
}