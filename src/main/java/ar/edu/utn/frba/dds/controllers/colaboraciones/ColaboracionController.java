package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.services.colaboraciones.ColaboracionService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ColaboradorPorSession;
import io.javalin.http.Context;

public class ColaboracionController extends ColaboradorPorSession {

    private ColaboracionService colaboracionService;

    public ColaboracionController(UsuarioService usuarioService,
                                  ColaboradorService colaboradorService,
                                  ColaboracionService colaboracionService) {
        super(usuarioService, colaboradorService);
        this.colaboracionService = colaboracionService;
    }

    public void cargarColaboraciones(Context context) {

    }
}
