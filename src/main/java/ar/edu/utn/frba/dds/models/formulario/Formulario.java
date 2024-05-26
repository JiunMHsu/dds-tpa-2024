package ar.edu.utn.frba.dds.models.formulario;

import java.util.ArrayList;

public class Formulario {

    private ArrayList<Pregunta> preguntas;

    public Formulario() {
        preguntas = new ArrayList<>();
    }

    public void agregarCampo(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    public void quitarCampo(Pregunta pregunta) {
        preguntas.remove(pregunta);
    }
}