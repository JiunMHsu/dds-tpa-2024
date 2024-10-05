package ar.edu.utn.frba.dds.controllers.session;

import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import ar.edu.utn.frba.dds.utils.RandomString;
import io.javalin.http.Context;

public class SessionController {

    private RandomString sessionIdGenetator; // capaz por inyección?
    private UsuarioRepository usuarioRepository;

    public SessionController(UsuarioRepository usuarioRepository) {
        this.sessionIdGenetator = new RandomString();
        this.usuarioRepository = usuarioRepository;
    }

    public void index(Context context) {
        // si hay session ya, redirect
        // si no hay, form para hacer el login
    }

    public void create(Context context) {
        // validar los datos y crear la session
        // indicar guardado de session id en cookie
        // redirect en caso de exito
        // vista de reintento en caso de falla
    }

    public void delete(Context context) {
        // borrar la session
    }
}
