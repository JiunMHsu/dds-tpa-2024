package ar.edu.utn.frba.dds.models.convertidorArchivos;

import ar.edu.utn.frba.dds.models.mailSender.MailSender;
import ar.edu.utn.frba.dds.models.colaborador.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter

public class RegistroColaboradoresPrevios {

  private CargadorColaboraciones conversor;
  private MailSender mailSender;
  private GeneradorDeCredencial generadorDeCredencial;
  private List<ColaboradoresPrevios> colaboradoresPrevios;

  public RegistroColaboradoresPrevios() {
    this.conversor = new CargadorColaboraciones();
    this.colaboradoresPrevios = new ArrayList<>();
  }

  public void generCredencial(String destinatario) {

    Usuario usuario = generadorDeCredencial.generCredencial(destinatario);

    String asunto = "Credencial de usuario";
    String cuerpo = "Esta es la credencial:" +
        " - Nombre de usuario provicional: " + usuario.getNombre() +
        " - Contrasenia de usuario provicional: " + usuario.getContrasenia();

    mailSender.enviarMail(destinatario, asunto, cuerpo);
  }

  public void cargarColaboraciones(Path path) {

    this.colaboradoresPrevios.addAll(conversor.convertirALista(path));
  }


  public List<ColaboradoresPrevios> colaboradoresNoRegistrados(List<ColaboradoresPrevios> listaEntrada) {

    List<ColaboradoresPrevios> noRegistrados = listaEntrada.stream()
        .filter(colaborador -> !listaEntrada.contains(colaborador))
        .collect(Collectors.toList());

    return noRegistrados;
  }

    /* public registrarColaboradores(){ // Para los q no tienen usuario
        // TODO
    } */

}
