package ar.edu.utn.frba.dds.controllers.colaborador;

import ar.edu.utn.frba.dds.dtos.colaboraciones.ColaboracionDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.TipoRazonSocial;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ColaboradorController implements ICrudViewsHandler {

    private final ColaboradorService colaboradorService;

    public ColaboradorController(ColaboradorService service) {
        this.colaboradorService = service;
    }

    @Override
    public void index(Context context) {
        //TODO verificar rol de admin
        List<Colaborador> colaboradores = this.colaboradorService.buscarTodos();

        Map<String, Object> model = new HashMap<>();
        model.put("colaboradores.hbs", colaboradores);
        model.put("titulo", "Listado de colaboradores");

        context.render("colaboradores/colaboradores.hbs", model);

    }

    @Override
    public void show(Context context) {

        //TODO verificar rol de admin
        //por id
        Optional<Colaborador> posibleColaboradorBuscado = this.colaboradorService.buscarPorId(context.formParam("id"));
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
        //TODO ver rol adminitrador
        Usuario usuario = Usuario.con(context.formParam("nombre"), context.formParam("contrasenia"), context.formParam("email"), TipoRol.COLABORADOR);
        Direccion direccion = Direccion.with(
                new Barrio(context.formParam("barrio")),
                new Calle(context.formParam("calle")),
                Integer.valueOf(context.formParam("altura")),
                new Ubicacion(Double.valueOf(context.formParam("latitud")), Double.valueOf(context.formParam("longitud")))
        );
        Contacto contacto = Contacto.con(
                context.formParam("email"),
                context.formParam("telefono"),
                context.formParam("whatsapp"),
                context.formParam("telegram")
        );

        String colaboracionesParam = context.formParam("colaboraciones");

        List<Colaboracion> colaboraciones = new ArrayList<>();
        if (colaboracionesParam != null && !colaboracionesParam.isEmpty()) {
            List<String> colaboracionesStr = Arrays.asList(colaboracionesParam.split(","));

            // Convertimos cada string en un valor del enum Colaboracion
            for (String colaboracionStr : colaboracionesStr) {
                try {
                    Colaboracion colaboracion = Colaboracion.valueOf(colaboracionStr.trim().toUpperCase());
                    colaboraciones.add(colaboracion);
                } catch (IllegalArgumentException e) {
                    //nunca va a pasar esto
                    System.out.println("Colaboración inválida: " + colaboracionStr);
                }
            }
        }

        Colaborador nuevoColaborador = Colaborador.colaborador(usuario, contacto, direccion, colaboraciones);

        String tipoColaborador = context.formParam("tipo_colaborador");
        if ("JURIDICO".equals(tipoColaborador)) {
            nuevoColaborador.setRazonSocial(context.formParam("razon_social"));
            nuevoColaborador.setTipoRazonSocial(TipoRazonSocial.valueOf(context.formParam("tipo_razon_social").toUpperCase()));
            nuevoColaborador.setRubro(context.formParam("rubro"));
        } else if ("HUMANA".equals(tipoColaborador)) {
            nuevoColaborador.setNombre(context.formParam("nombre"));
            nuevoColaborador.setApellido(context.formParam("apellido"));

            // Obtener fecha de nacimiento (suponiendo que el formulario tenga campos separados para día, mes, año)
            int diaNacimiento = Integer.parseInt(context.formParam("dia_nacimiento"));
            int mesNacimiento = Integer.parseInt(context.formParam("mes_nacimiento"));
            int anioNacimiento = Integer.parseInt(context.formParam("anio_nacimiento"));

            // Crear el objeto LocalDate para la fecha de nacimiento
            LocalDate fechaNacimiento = LocalDate.of(anioNacimiento, mesNacimiento, diaNacimiento);
            nuevoColaborador.setFechaNacimiento(fechaNacimiento);
        }

        this.colaboradorService.guardar(nuevoColaborador);
        context.redirect("/colaboradores/sign_up_exitoso.hbs");
    }

    @Override
    public void edit(Context context) {
        // por handlebars
    }

    @Override
    public void update(Context context) {
        //esto teniendo en cuenta solo una forma de colaboracion por formulario
        Optional<Colaborador> posibleColaboradorActualizar = this.colaboradorService.buscarPorId(context.formParam("id"));
        // TODO - chequeo si no existe

        Colaborador colaboradorActualizado = posibleColaboradorActualizar.get();
        colaboradorActualizado.agregarFormaColaborar(Colaboracion.valueOf(context.formParam("nueva_forma_colaborar")));
        this.colaboradorService.actualizar(colaboradorActualizado);
        context.status(HttpStatus.OK);
    }

    @Override
    public void delete(Context context) {
        Optional<Colaborador> posibleColaboradorAEliminar = this.colaboradorService.buscarPorId(context.formParam("id"));
        // TODO - chequeo si no existe

        this.colaboradorService.eliminar(posibleColaboradorAEliminar.get());
        context.status(HttpStatus.OK);
        // mostrar algo de exitoso

    }

    public void editFormasDeColaborar(Context context) {

        TipoRol userRol = TipoRol.valueOf(context.sessionAttribute("userRol"));
        String userId = context.sessionAttribute("userId");

        String pathId = context.pathParam("id");

        System.out.println(userRol);
        System.out.println(userId);
        System.out.println(pathId);

        if (userRol != TipoRol.ADMIN || !Objects.equals(userId, pathId))
            throw new UnauthorizedException();

        Optional<Colaborador> colaboradorBuscado = colaboradorService.buscarPorId(pathId);
        if (colaboradorBuscado.isEmpty()) throw new ResourceNotFoundException();

        Colaborador colaborador = colaboradorBuscado.get();
        List<Colaboracion> formasRegistradas = colaborador.getFormaDeColaborar();
        List<Colaboracion> formasPermitidas = colaborador.getTipoColaborador().colaboracionesPermitidas();

        List<ColaboracionDTO> colaboracionDTOS = formasPermitidas.stream()
                .map(c -> ColaboracionDTO.fromColaboracion(c, formasRegistradas.contains(c)))
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("id", pathId);
        model.put("colaboraciones", colaboracionDTOS);

        context.render("colaboradores/formas_de_colaboracion_editar.hbs", model);
    }

    public void updateFormasDeColaborar(Context context) {
    }

}
