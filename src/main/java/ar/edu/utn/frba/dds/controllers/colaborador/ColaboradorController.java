package ar.edu.utn.frba.dds.controllers.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ColaboradorController implements ICrudViewsHandler {

    private ColaboradorRepository colaboradorRepository;

    public ColaboradorController(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    @Override
    public void index(Context context) {
        //TODO verificar rol de admin
        List<Colaborador> colaboradores = this.colaboradorRepository.obtenerTodos();

        Map<String, Object> model = new HashMap<>();
        model.put("colaboradores.hbs", colaboradores);
        model.put("titulo", "Listado de colaboradores.hbs");

        context.render("colaboradores/colaboradores.hbs", model);

    }

    @Override
    public void show(Context context) {

        //TODO verificar rol de admin
        //por id
        Optional<Colaborador> posibleColaboradorBuscado = this.colaboradorRepository.obtenerPorId(UUID.fromString(context.pathParam("id")));
        //TODO verificar empty
        if (posibleColaboradorBuscado.isEmpty()) {
            context.status(404);//not found
            return;
        }
        Map<String, Object> model = new HashMap<>();
        model.put("colaborador", posibleColaboradorBuscado.get());

        context.render("colaboradores/colaborador_detalle.hbs", model);

    }

    @Override
    public void create(Context context) {
        //sign up
        //TODO ver porque en nuestro diseño eran muchas vistas distintas
        context.render("colaboradores/sign_up.hbs");

    }

    @Override
    public void save(Context context) {
        //TODO ver rol
        Usuario usuario = Usuario.con(context.pathParam("nombre"), context.pathParam("contrasenia"), context.pathParam("email"), TipoRol.ADMIN);
        Colaborador nuevoColaborador = Colaborador.colaborador(usuario);
        //ver datos adicionales
        if (context.pathParam("tipo_colaborador").equals("humano")) {
//            nuevoColaborador.
        }

        this.colaboradorRepository.guardar(nuevoColaborador);
        //O BIEN LANZO UNA PANTALLA DE EXITO
        //O BIEN REDIRECCIONO AL USER A LA PANTALLA DE LISTADO DE PRODUCTOS
        context.redirect("colaboradores/sign_up_exitoso.hbs");


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
