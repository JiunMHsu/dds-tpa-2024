package ar.edu.utn.frba.dds.models.convertidorArchivos;

import ar.edu.utn.frba.dds.models.mailSender.MailSender;
import ar.edu.utn.frba.dds.models.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter

public class RegistroColaboradoresPrevios {

    private CargadorColaboraciones conversor;
    private MailSender mailSender;
    private GeneradorDeCredencial  generadorDeCredencial;
    private List<ColaboradoresPrevios> colaboradoresPrevios;

    public RegistroColaboradoresPrevios() {
        this.colaboradoresPrevios = new ArrayList<>();
    }
    public void generCredencial(String destinatario, String credencial){

         Usuario usuario = generadorDeCredencial.generCredencial(destinatario); 

         String asunto = "Credencial de usuario";
         String cuerpo = "Esta es la credencial:" +
                         " - Nombre de usuario provicional: " + usuario.getNombreUsuario() +
                         " - Contrasenia de usuario provicional: " + usuario.getContrasenia();

         mailSender.enviarMail(destinatario, asunto, cuerpo);
    }

   /* public cargarColaboraciones(){
        // TODO
    }
    */

    public List<ColaboradoresPrevios> colaboradoresNoRegistrados(){

        List<ColaboradoresPrevios> noRegistrados = colaboradoresPrevios.stream()
                .filter(colaborador -> !colaborador.estaRegistrado())
                .collect(Collectors.toList());

        return noRegistrados;
    }

    /* public registrarColaboradores(){ // Para los q no tienen usuario
        // TODO
    } */

}
