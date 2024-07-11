package ar.edu.utn.frba.dds.models.colaborador;

import lombok.Getter;

@Getter
public class Usuario {

  private String nombre;
  private String contrasenia;
  private String email;

  public Usuario(String nombreUsuario, String contrasenia, String email) {
    this.nombre = nombreUsuario;
    this.contrasenia = contrasenia;
    this.email = email;
  }
}