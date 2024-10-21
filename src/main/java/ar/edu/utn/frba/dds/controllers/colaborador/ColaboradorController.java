package ar.edu.utn.frba.dds.controllers.colaborador;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
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
import io.javalin.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ColaboradorController implements ICrudViewsHandler {
    private final UsuarioService usuarioService;
    private final ColaboradorService colaboradorService;

    public ColaboradorController(UsuarioService usuarioService, ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
        this.usuarioService = usuarioService;
    }

    @Override
    public void index(Context context) {
        //TODO verificar rol de admin
        List<Colaborador> colaboradores = this.colaboradorService.buscarTodosColaboradores();

        Map<String, Object> model = new HashMap<>();
        model.put("colaboradores.hbs", colaboradores);
        model.put("titulo", "Listado de colaboradores");

        context.render("colaboradores/colaboradores.hbs", model);

    }

    @Override
    public void show(Context context) {

        //TODO verificar rol de admin
        String colaboradorId = context.pathParam("id");
        Optional<Colaborador> posibleColaboradorBuscado = this.colaboradorService.obtenerColaboradorPorID(colaboradorId);
      
        //TODO verificar empty
        if (posibleColaboradorBuscado.isEmpty()) {
            context.status(404);//not found
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("colaborador", posibleColaboradorBuscado.get());

        context.render("colaboradores/colaborador_detalle.hbs", model);

    }

    public void getProfile(Context context){

        String usuarioId = context.sessionAttribute("userId");
        Optional<Usuario> usuario = this.usuarioService.obtenerUsuarioPorID(usuarioId);
        Optional<Colaborador> posibleColaboradorBuscado = this.colaboradorService.obtenerColaboradorPorUsuario(usuario.get());

        //TODO verificar empty
        if (posibleColaboradorBuscado.isEmpty()) {
            throw new ResourceNotFoundException("No encontrado");
        }

        Map<String, Object> model = new HashMap<>();
        model.put("colaborador", posibleColaboradorBuscado.get());
        model.put("isJuridico", false);
        /*
        model.put("isJuridico", posibleColaboradorBuscado.get().getTipoColaborador().getTipo() == TipoPersona.JURIDICO);
        */
        context.render("perfil/perfil.hbs", model);

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

        ArrayList<Colaboracion> colaboraciones = new ArrayList<>();
        if (colaboracionesParam != null && !colaboracionesParam.isEmpty()) {
            String[] colaboracionesStr = colaboracionesParam.split(",");

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

        this.colaboradorService.guardarColaborador(nuevoColaborador);
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
        // colaboradorActualizado.agregarFormaColaborar(Colaboracion.valueOf(context.formParam("nueva_forma_colaborar")));
        this.colaboradorService.actualizar(colaboradorActualizado);
        context.status(HttpStatus.OK);
    }

    @Override
    public void delete(Context context) {
        String colaboradorId = context.pathParam("id");
        Optional<Colaborador> posibleColaboradorAEliminar = this.colaboradorService.obtenerColaboradorPorID(colaboradorId);


        /*// TODO - chequeo si no existe

        this.colaboradorService.eliminarColaborador(posibleColaboradorAEliminar.get());
        context.status(HttpStatus.OK);
        // mostrar algo de exitoso*/

        /*ALGO ASI?*/
        if (posibleColaboradorAEliminar.isPresent()) {
            this.colaboradorService.eliminarColaborador(posibleColaboradorAEliminar.get());
            context.status(HttpStatus.OK);
            context.result("Colaborador eliminado exitosamente.");
        } else {
            context.status(HttpStatus.NOT_FOUND);
            context.result("Colaborador no encontrado.");
        }

    }

    public void editFormasDeColaborar(Context context) {

        // TODO - refactorizar
        TipoRol userRol = TipoRol.valueOf(context.sessionAttribute("userRol"));
        String userId = context.sessionAttribute("userId");

        String pathId = context.pathParam("id");

        if (userRol == TipoRol.COLABORADOR && !Objects.equals(userId, pathId))
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
        TipoRol userRol = TipoRol.valueOf(context.sessionAttribute("userRol"));
        String userId = context.sessionAttribute("userId");

        String pathId = context.pathParam("id");

        if (userRol == TipoRol.COLABORADOR && !Objects.equals(userId, pathId))
            throw new UnauthorizedException();

        Optional<Colaborador> colaboradorBuscado = colaboradorService.buscarPorId(pathId);
        if (colaboradorBuscado.isEmpty()) throw new ResourceNotFoundException();

        Colaborador colaborador = colaboradorBuscado.get();

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {
            List<String> colaboracionesForm = context.formParams("colaboracion");
            ArrayList<Colaboracion> colaboraciones = colaboracionesForm.stream()
                    .map(Colaboracion::valueOf).collect(Collectors.toCollection(ArrayList::new));

            System.out.println(colaboraciones);

            colaborador.setFormaDeColaborar(colaboraciones);
            colaboradorService.actualizar(colaborador);

            operationSuccess = true;

        } catch (ValidationException e) {
            redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }
    }

}
