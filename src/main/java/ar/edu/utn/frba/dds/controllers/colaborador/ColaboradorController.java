package ar.edu.utn.frba.dds.controllers.colaborador;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.TipoColaboracionDTO;
import ar.edu.utn.frba.dds.dtos.colaborador.ColaboradorDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.Puntos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.TipoRazonSocial;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
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
        Map<String, Object> model = new HashMap<>();

        List<Colaborador> colaboradores = this.colaboradorService.buscarTodosColaboradores();

        List<ColaboradorDTO> colaboradoresDTO = colaboradores.stream()
                .map(ColaboradorDTO::preview)
                .toList();
        model.put("colaboradores", colaboradoresDTO);

        context.result("PENDIENTE");

        // TODO - vista listado de colaboradores
        // context.render("colaboradores/colaboradores.hbs", model);
    }

    @Override
    public void show(Context context) {
        String colaboradorId = context.pathParam("id");
        Colaborador colaborador = this.colaboradorService
                .obtenerColaboradorPorID(colaboradorId)
                .orElseThrow(ResourceNotFoundException::new);

        Map<String, Object> model = new HashMap<>();
        ColaboradorDTO colaboradorDTO = ColaboradorDTO.completa(colaborador);
        model.put("colaborador", colaboradorDTO);

        context.result("PENDIENTE");

        // TODO - vista detalle de colaborador
        // context.render("colaboradores/colaborador_detalle.hbs", model);

    }

    // TODO - REVISAR
    public void getProfile(Context context) {

        String usuarioId = context.sessionAttribute("userId");
        Optional<Usuario> usuario = this.usuarioService.obtenerUsuarioPorID(usuarioId);
        Optional<Colaborador> posibleColaboradorBuscado = this.colaboradorService.obtenerColaboradorPorUsuario(usuario.get());

        if (posibleColaboradorBuscado.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró colaborador paraColaborador usuario por id " + usuarioId);
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
        context.render("signs/sign.hbs");
    }


    @Override
    public void save(Context context) {

    }

    public void saveHumana(Context context) { // TODO - Se puede hacer mejor seguramente pero x ahora anda 👌

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {

            Usuario usuario = Usuario.con(
                    context.formParamAsClass("nombre_usuario", String.class).get(),
                    context.formParamAsClass("contrasenia", String.class).get(),
                    context.formParamAsClass("email", String.class).get(),
                    TipoRol.COLABORADOR
            );

            Direccion direccion = Direccion.with(
                    new Barrio(context.formParamAsClass("barrio", String.class).get()),
                    new Calle(context.formParamAsClass("calle", String.class).get()),
                    Integer.valueOf(context.formParamAsClass("altura", String.class).get())
            );

            Contacto contacto = Contacto.con(
                    context.formParamAsClass("email", String.class).get(),
                    context.formParamAsClass("telefono", String.class).get(),
                    "whatsapp:" + context.formParamAsClass("whatsapp", String.class).get(),
                    context.formParamAsClass("telegram", String.class).get()
            );

            List<String> colaboracionesSeleccionadas = context.formParams("colaboraciones");
            ArrayList<TipoColaboracion> formasDeColaborar = new ArrayList<>();

            for (String colaboracion : colaboracionesSeleccionadas) {
                formasDeColaborar.add(TipoColaboracion.valueOf(colaboracion));
            }

            Colaborador colaboradorNuevo = Colaborador.humana(
                    usuario,
                    context.formParamAsClass("nombre", String.class).get(),
                    context.formParamAsClass("apellido", String.class).get(),
                    LocalDate.parse(context.formParamAsClass("fecha_nacimiento", String.class).get()),
                    contacto,
                    direccion,
                    formasDeColaborar,
                    new Puntos(0, false, null)
            );

            this.colaboradorService.guardar(colaboradorNuevo);

            operationSuccess = true;

        } catch (ValidationException v) {
            redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }
    }

    public void saveJuridica(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {

            Usuario usuario = Usuario.con(
                    context.formParamAsClass("nombre_usuario", String.class).get(),
                    context.formParamAsClass("contrasenia", String.class).get(),
                    context.formParamAsClass("email", String.class).get(),
                    TipoRol.COLABORADOR
            );

            Direccion direccion = Direccion.with(
                    new Barrio(context.formParamAsClass("barrio", String.class).get()),
                    new Calle(context.formParamAsClass("calle", String.class).get()),
                    Integer.valueOf(context.formParamAsClass("altura", String.class).get())
            );

            Contacto contacto = Contacto.con(
                    context.formParamAsClass("email", String.class).get(),
                    context.formParamAsClass("telefono", String.class).get(),
                    "whatsapp:+" + context.formParamAsClass("whatsapp", String.class).get(),
                    context.formParamAsClass("telegram", String.class).get()
            );

            List<String> colaboracionesSeleccionadas = context.formParams("colaboraciones");
            ArrayList<TipoColaboracion> formasDeColaborar = new ArrayList<>();

            for (String colaboracion : colaboracionesSeleccionadas) {
                formasDeColaborar.add(TipoColaboracion.valueOf(colaboracion));
            }
            
            Colaborador colaboradorNuevo = Colaborador.juridica(
                    usuario,
                    context.formParamAsClass("razon_social", String.class).get(),
                    TipoRazonSocial.valueOf(context.formParamAsClass("tipo_razon_social", String.class).get()),
                    context.formParamAsClass("rubro", String.class).get(),
                    contacto,
                    direccion,
                    formasDeColaborar,
                    new Puntos(0, false, null)
            );

            this.colaboradorService.guardar(colaboradorNuevo);

            operationSuccess = true;

        } catch (ValidationException v) {
            redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }

    }


    @Override
    public void edit(Context context) {
    }

    @Override
    public void update(Context context) { // TODO - REFACTOR
        String colaboradorId = context.pathParam("id");
        Optional<Colaborador> posibleColaborador = this.colaboradorService.obtenerColaboradorPorID(colaboradorId);

        if (posibleColaborador.isEmpty())
            throw new ResourceNotFoundException("No se encontró colaborador paraColaborador id " + colaboradorId);

        Colaborador colaboradorActualizado = posibleColaborador.get();
        // TODO ver agregar forma colaborar
        //  colaboradorActualizado.agregarFormaColaborar(Colaboracion.valueOf(context.formParam("nueva_forma_colaborar")));
        this.colaboradorService.actualizar(colaboradorActualizado);
        context.status(HttpStatus.OK);
    }

    @Override
    public void delete(Context context) {
        String colaboradorId = context.pathParam("id");
        Optional<Colaborador> posibleColaboradorAEliminar = this.colaboradorService.obtenerColaboradorPorID(colaboradorId);

        if (posibleColaboradorAEliminar.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró colaborador paraColaborador id " + colaboradorId);
        }

        this.colaboradorService.eliminarColaborador(posibleColaboradorAEliminar.get());
        context.status(HttpStatus.OK);
        // mostrar algo por exitoso*/

    }

    public void editFormasDeColaborar(Context context) {
        String pathId = context.pathParam("id");
        Colaborador colaborador = restrictByOwner(context, pathId);

        List<TipoColaboracion> formasRegistradas = colaborador.getFormaDeColaborar();
        System.out.println(formasRegistradas);
        List<TipoColaboracion> formasPermitidas = colaborador.getTipoColaborador().colaboracionesPermitidas();

        List<TipoColaboracionDTO> colaboracionDTOS = formasPermitidas.stream()
                .map(c -> TipoColaboracionDTO.configOption(c, formasRegistradas.contains(c)))
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("id", pathId);
        model.put("colaboraciones", colaboracionDTOS);

        context.render("colaboradores/formas_de_colaboracion_editar.hbs", model);
    }

    public void updateFormasDeColaborar(Context context) {
        Colaborador colaborador = restrictByOwner(context, context.pathParam("id"));

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {
            List<String> colaboracionesForm = context.formParams("colaboracion");
            ArrayList<TipoColaboracion> colaboraciones = colaboracionesForm.stream()
                    .map(TipoColaboracion::valueOf).collect(Collectors.toCollection(ArrayList::new));

            System.out.println(colaboraciones);

            colaborador.setFormaDeColaborar(colaboraciones);
            colaboradorService.actualizar(colaborador);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/colaboraciones", "Colaborar"));

        } catch (ValidationException e) {
            redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }
    }

    private Colaborador restrictByOwner(Context context, String colaboradorId) {
        String userId = context.sessionAttribute("userId");

        Colaborador colaborador = colaboradorService
                .obtenerColaboradorPorID(colaboradorId)
                .orElseThrow(ResourceNotFoundException::new);

        if (!Objects.equals(colaborador.getUsuario().getId().toString(), userId))
            throw new UnauthorizedException();

        return colaborador;
    }

}
