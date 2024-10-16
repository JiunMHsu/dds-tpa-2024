package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.colaborador.ColaboradorController;
import ar.edu.utn.frba.dds.controllers.session.SessionController;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import io.javalin.Javalin;

public class Router {

    // TEMP
    private static final UsuarioRepository usuarioRepository = new UsuarioRepository();
    private static final ColaboradorRepository colaboradorRepository = new ColaboradorRepository();

    public static void init(Javalin app) {
        app.get("/", ctx -> ctx.redirect("/pagina_inicial"));

        app.get("/login", new SessionController(usuarioRepository)::index); /* no tendria que ser create*/
        app.post("/login", new SessionController(usuarioRepository)::create);/* no tendria que ser save?*/

        app.get("/pagina_inicial", ctx -> ctx.render("inicial/pagina_inicial.hbs"));
        app.get("/sign", ctx -> ctx.render("signs/sign.hbs"));

        app.get("/signHumana", ctx -> ctx.render("signs/signHumana.hbs"));
        app.get("/signJuridica", ctx -> ctx.render("signs/signJuridica.hbs"));

        app.post("/login", new ColaboradorController(colaboradorRepository)::save);


        app.post("/save", ServiceLocator.instanceOf(ColaboradorController.class)::save);

        app.get("/home", ctx -> ctx.render("home/home.hbs"));

        app.get("/heladeras/new", ctx -> ctx.render("heladeras/heladera_crear.hbs"));

        app.get("/heladeras/unId", ctx -> ctx.render("heladeras/heladera_detalle.hbs"));

        app.get("/heladeras", ctx -> ctx.render("heladeras/heladeras.hbs"));

        app.get("/alertas", ctx -> ctx.render("alertas/alertas.hbs"));

        app.get("/colaboraciones/donacion-dinero/new", ctx -> ctx.render("colaboraciones/donacion_dinero_crear.hbs"));
        app.get("/colaboraciones/donacion-vianda/new", ctx -> ctx.render("colaboraciones/donacion_vianda_crear.hbs"));
        app.get("/colaboraciones/registro-persona-vulnerable/new", ctx -> ctx.render("colaboraciones/registro_pv_crear.hbs"));
        app.get("/colaboraciones/entrega-viandas/new", ctx -> ctx.result("PENDIENTE"));
        app.get("/colaboraciones/distribucion-viandas/new", ctx -> ctx.render("colaboraciones/distribucion_viandas_crear.hbs"));
        app.get("/colaboraciones/oferta-producto-servicio/new", ctx -> ctx.render("colaboraciones/oferta_prod_serv_crear.hbs"));
        app.get("/colaboraciones/encargarse-de-heladeras/new", ctx -> ctx.render("colaboraciones/encargarse_de_heladera_crear.hbs"));

        app.get("/colaboraciones", ctx -> ctx.render("colaboraciones/colaboraciones.hbs"));

        app.get("/test", ctx -> ctx.result("DDS TPA"));
        app.get("/image/{id}", ctx -> ctx.result("IMAGEN"));

        /*
        app.get("/sign_up_humana", ServiceLocator.instanceOf(SignHumanaController.class)::create);
        app.post("/sign_up_humana", ServiceLocator.instanceOf(SignHumanaController.class)::save);*/
    }

}
