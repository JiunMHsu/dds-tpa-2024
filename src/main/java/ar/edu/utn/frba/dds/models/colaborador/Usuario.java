package ar.edu.utn.frba.dds.models.colaborador;

import ar.edu.utn.frba.dds.models.data.Mail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {

  private String nombre;
  private String contrasenia;
  private Mail email;

  public Usuario(String nombreUsuario, String contrasenia, Mail email) {
    this.nombre = nombreUsuario;
    this.contrasenia = contrasenia;
    this.email = email;
  }

}