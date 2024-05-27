package ar.edu.utn.frba.dds.models.convertidorArchivos;

import ar.edu.utn.frba.dds.models.usuario.Usuario;

public class GeneradorDeCredencial {

    public Usuario generCredencial(String mail) {
        Usuario nuevoUsuario = new Usuario(); // Momentaneo ya que para mi el usuario
                                              // lo deberia crear la persona en si en otra capa
        return nuevoUsuario;
    }
}
