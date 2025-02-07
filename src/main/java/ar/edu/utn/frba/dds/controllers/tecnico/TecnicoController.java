package ar.edu.utn.frba.dds.controllers.tecnico;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.CreateTecnicoDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.TecnicoDTO;
import ar.edu.utn.frba.dds.dtos.usuario.CreateUsuarioDTO;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import ar.edu.utn.frba.dds.models.stateless.ValidadorDeContrasenias;
import ar.edu.utn.frba.dds.permissions.TecnicoRequired;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de la entidad Tecnico.
 */
public class TecnicoController extends TecnicoRequired {

  /**
   * Constructor de TecnicoController.
   *
   * @param usuarioService Servicio de Usuario
   * @param tecnicoService Servicio de Tecnico
   */
  public TecnicoController(UsuarioService usuarioService,
                           TecnicoService tecnicoService) {
    super(usuarioService, tecnicoService);
  }

  /**
   * Devuelve la vista de todos los técnicos.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();

    List<TecnicoDTO> tecnicos = this.tecnicoService.buscarTodos();
    model.put("tecnicos", tecnicos);

    render(context, "tecnicos/tecnicos.hbs", model);
  }

  /**
   * Devuelve la vista de un Técnico.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void show(Context context) {
    String idTecnico = context.pathParam("id");
    TecnicoDTO tecnico = this.tecnicoService.buscarTecnicoPorId(idTecnico);

    Map<String, Object> model = new HashMap<>();
    model.put("tecnico", tecnico);

    render(context, "tecnicos/tecnico_detalle.hbs", model);
  }

  /**
   * Devuelve un formulario para dar de alta a un Técnico.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void create(Context context) {
    render(context, "tecnicos/tecnico_crear.hbs", new HashMap<>());
  }

  /**
   * Registra a un Técnico.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      String contrasenia = context.formParamAsClass("contrasenia", String.class).get();
      ValidadorDeContrasenias validador = new ValidadorDeContrasenias();

      CreateUsuarioDTO nuevoUsuario;

      if (!validador.esValida(contrasenia)) {
        throw new ar.edu.utn.frba.dds.exceptions.ValidationException("");
      } else {
        nuevoUsuario = CreateUsuarioDTO.con(
            context.formParamAsClass("nombre_usuario", String.class).get(),
            contrasenia,
            context.formParamAsClass("email", String.class).get(),
            TipoRol.TECNICO.toString()
        );
      }

      CreateTecnicoDTO nuevoTecnico = new CreateTecnicoDTO(
          context.formParamAsClass("nombre", String.class).get(),
          context.formParamAsClass("apellido", String.class).get(),
          context.formParamAsClass("tipo_documento", String.class).get(),
          context.formParamAsClass("nro_documento", String.class).get(),
          context.formParamAsClass("cuil", String.class).get(),
          context.formParamAsClass("latitud", Double.class).get(),
          context.formParamAsClass("longitud", Double.class).get(),
          context.formParamAsClass("radio", Integer.class).get(),
          context.formParamAsClass("barrio", String.class).get()
      );

      this.tecnicoService.registrarNuevoTecnico(nuevoUsuario, nuevoTecnico);

      operationSuccess = true;
      redirects.add(new RedirectDTO("/home", "Siguiente"));

    } catch (ValidationException | IllegalArgumentException e) {
      redirects.add(new RedirectDTO("/home", "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);
      context.render("post_result.hbs", model);
    }
  }
}
