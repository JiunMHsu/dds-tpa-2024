package ar.edu.utn.frba.dds.controllers.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.*;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

public class ColaboradorController implements ICrudViewsHandler {

    private ColaboradorRepository colaboradorRepository;

    public ColaboradorController(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    @Override
    public void index(Context context) {
        //TODO verificar rol de admin
        List<Colaborador> colaboradores = this.colaboradorRepository.buscarTodos();

        Map<String, Object> model = new HashMap<>();
        model.put("colaboradores.hbs", colaboradores);
        model.put("titulo", "Listado de colaboradores.hbs");

        context.render("colaboradores/colaboradores.hbs", model);

    }

    @Override
    public void show(Context context) {

        //TODO verificar rol de admin
        //por id
        Optional<Colaborador> posibleColaboradorBuscado = this.colaboradorRepository.buscarPorId(context.pathParam("id"));
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
        Direccion direccion = Direccion.with(
                new Barrio(context.formParam("barrio")),
                new Calle(context.formParam("calle")),
                Integer.valueOf(context.formParam("altura")),
                new Ubicacion(Double.valueOf(context.formParam("latitud")), Double.valueOf(context.formParam("longitud")))
        );
        Contacto contacto = Contacto.con(context.pathParam("email"),context.pathParam("telefono"),context.pathParam("whatsapp"),context.pathParam("telegram"));
        Colaborador nuevoColaborador = Colaborador.colaborador(usuario,contacto, direccion , new ArrayList<Colaboracion>());
        //TODO las formas de colaborar como las obtengo de la plantilla?
        //ver datos adicionales

        if (context.pathParam("tipo_colaborador").equals("JURIDICO")) {
            nuevoColaborador.setRazonSocial(context.pathParam("razon_social"));
            nuevoColaborador.setTipoRazonSocial(TipoRazonSocial.valueOf(context.pathParam("tipo_razon_social")));
            nuevoColaborador.setRubro(context.pathParam("rubro"));
        } else if (context.pathParam("tipo_colaborador").equals("HUMANA")) {
            nuevoColaborador.setNombre(context.formParam("nombre"));
            nuevoColaborador.setApellido(context.formParam("apellido"));
            //nuevoColaborador.setFechaNacimiento(); TODO fecha la obtengo por separado cada parte o como
        }

        this.colaboradorRepository.guardar(nuevoColaborador);
        //O BIEN LANZO UNA PANTALLA DE EXITO
        //O BIEN REDIRECCIONO AL USER A LA PANTALLA DE LISTADO DE PRODUCTOS
        context.redirect("colaboradores/sign_up_exitoso.hbs");


    }

    @Override
    public void edit(Context context) {
        // por handlebars
    }

    @Override
    public void update(Context context) {
        //esto teniendo en cuenta solo una forma de colaboracion por formulario
        Optional<Colaborador> posibleColaboradorActualizar = this.colaboradorRepository.buscarPorId(context.pathParam("id"));
        // TODO - chequeo si no existe

        Colaborador colaboradorActualizado = posibleColaboradorActualizar.get();
        colaboradorActualizado.agregarFormaColaborar(Colaboracion.valueOf(context.formParam("nueva_forma_colaborar")));
        this.colaboradorRepository.actualizar(colaboradorActualizado);
        context.status(HttpStatus.OK);
    }

    @Override
    public void delete(Context context) {
        Optional<Colaborador> posibleColaboradorAEliminar = this.colaboradorRepository.buscarPorId(context.pathParam("id"));
        // TODO - chequeo si no existe

        this.colaboradorRepository.eliminar(posibleColaboradorAEliminar.get());
        context.status(HttpStatus.OK);
        // mostrar algo de exitoso

    }
    //TODO agregue porque necesito para canje de puntos, pero revisar
    public Colaborador colaboradorPorId(String id){
        Optional<Colaborador> colaboradorBuscado = colaboradorRepository.buscarPorId(id);
        //TODO if empty
        return colaboradorBuscado.get();
    }

}
