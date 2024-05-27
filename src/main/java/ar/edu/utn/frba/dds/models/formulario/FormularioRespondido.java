package ar.edu.utn.frba.dds.models.formulario;

import java.util.ArrayList;
import lombok.Getter;

@Getter
public class FormularioRespondido {

    private ArrayList<Respuesta> respuestas;

    private Formulario formulario;

    public FormularioRespondido(Formulario formulario) {
        this.formulario = formulario;
        this.respuestas = new ArrayList<>();
    }

    public void responderFormulario(ArrayList<Respuesta> respuestas) {
        // debería ser respuesta al formulario entero o pregunta por pregunta?
        this.respuestas.addAll(respuestas);
    }
}