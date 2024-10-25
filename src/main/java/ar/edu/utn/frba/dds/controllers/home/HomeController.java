package ar.edu.utn.frba.dds.controllers.home;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.colaborador.TipoColaborador;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.services.home.HomeService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HomeController implements ICrudViewsHandler {

    HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @Override
    public void index(Context context) {

        String idUsuario = context.sessionAttribute("idUsuario");

        if (idUsuario == null) {
            context.redirect("/login");
            return;
        }

        Optional<Usuario> usuarioOpt = this.homeService.buscarPorId(idUsuario);
        if (!usuarioOpt.isPresent()) {
            context.redirect("/login");
            return;
        }

        Usuario usuario = usuarioOpt.get();

        if (usuario.getRol() != TipoRol.COLABORADOR) {
            context.redirect("/prohibido");
            return;
        }

        Optional<Colaborador> colaboradorOpt = this.homeService.buscarPorUsuario(usuario);

        if (!colaboradorOpt.isPresent()) {
            context.redirect("/login");
            return;
        }

        Colaborador colaborador = colaboradorOpt.get();
        TipoColaborador tipoColaborador = colaborador.getTipoColaborador();
        List<Colaboracion> colaboraciones = tipoColaborador.colaboracionesPermitidas();

        Map<String, Object> model = new HashMap<>();
        model.put("colaboraciones", colaboraciones);
        model.put("titulo", "Listado por colaboraciones");

        context.render("home.hbs", model);
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
