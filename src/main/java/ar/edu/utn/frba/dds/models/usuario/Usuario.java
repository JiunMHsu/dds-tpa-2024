package ar.edu.utn.frba.dds.models.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Usuario {

    private String nombreUsuario;
    private String contrasenia;
    private String email;

    public Usuario(String nombreUsuario, String contrasenia, String email) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.email = email;
    }

}