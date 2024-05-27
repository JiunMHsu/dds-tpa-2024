package ar.edu.utn.frba.dds.models.convertidorArchivos;

import ar.edu.utn.frba.dds.models.mailSender.MailSender;

import java.util.List;
import java.util.stream.Collectors;

public class RegistroColaboradoresPrevios {

    private CargadorColaboraciones conversor;
    private MailSender mailSender;
    private List<ColaboradoresPrevios> colaboradoresPrevios;


    public otorgarCredencialA(){
        /* TODO
           falta el mailSender pero seria enviar mail a los no registrados
         */
    }

   /* public cargarColaboraciones(){
        // TODO
    }
    */

    public colaboradoresNoRegistrados(){

        colaboradoresPrevios.stream()
                            .filter(colaborador -> !colaborador.estaRegistrado())
                            .collect(Collectors.toList());
    }

    /* public registrarColaboradores(){ // Para los q no tienen usuario
        // TODO
    } */

}
