package ar.edu.utn.frba.dds.controllers.tecnico;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.CreateTecnicoDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.TecnicoDTO;
import ar.edu.utn.frba.dds.dtos.usuario.CreateUsuarioDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.stateless.ValidadorDeContrasenias;
import ar.edu.utn.frba.dds.permissions.TecnicoRequired;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    List<Tecnico> tecnicos = this.tecnicoService.buscarTodos();
    List<TecnicoDTO> tecnicosDTO = tecnicos.stream()
        .map(TecnicoDTO::preview)
        .toList();

    Map<String, Object> model = new HashMap<>();
    model.put("tecnicos", tecnicosDTO);
    render(context, "tecnicos/tecnicos.hbs", model);
  }

  /**
   * Devuelve la vista de un técnico.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void show(Context context) {
    String idTecnico = context.pathParam("id");
    Optional<Tecnico> tecnicoBuscado = this.tecnicoService.buscarTecnicoPorId(idTecnico);

    if (tecnicoBuscado.isEmpty()) {
      throw new ResourceNotFoundException("No se encontró tecnico paraColaborador cuit " + idTecnico);
    }

    Map<String, Object> model = new HashMap<>();
    TecnicoDTO tecnicoDTO = TecnicoDTO.completa(tecnicoBuscado.get());
    model.put("tecnico", tecnicoDTO);

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
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      String contrasenia = context.formParamAsClass("contrasenia", String.class).get();
      ValidadorDeContrasenias validador = new ValidadorDeContrasenias();

      CreateUsuarioDTO nuevoUsuario;

      if (!validador.esValida(contrasenia)) {
        throw new ar.edu.utn.frba.dds.exceptions.ValidationException("La contraseña no cumple con los requisitos de seguridad.");
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
      redirectDTOS.add(new RedirectDTO("/home", "Siguiente"));

    } catch (ValidationException | IllegalArgumentException e) {
      redirectDTOS.add(new RedirectDTO("/home", "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
      context.render("post_result.hbs", model);
    }
  }

  /**
   * Devuelve un formulario para editar a un Técnico.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void update(Context context) { // TODO - REFACTOR

    String userId = context.sessionAttribute("userId");

    Optional<Usuario> usuarioSession = this.usuarioService.obtenerUsuarioPorId(userId);
    if (usuarioSession.isEmpty()) {
      throw new ResourceNotFoundException("No se encontró el usuario paraColaborador id " + userId);
    }

    Usuario usuario = usuarioSession.get();

    Optional<Tecnico> tecnicoSession = this.tecnicoService.obtenerTecnicoPorUsuario(usuarioSession.get());
    if (tecnicoSession.isEmpty()) {
      throw new ResourceNotFoundException("No se encontró el tecnico paraColaborador usuario " + usuario.getNombre());
    }

    Tecnico tecnicoActualizado = tecnicoSession.get();


    this.tecnicoService.actualizar(tecnicoActualizado);
    context.status(HttpStatus.OK); // TODO - mepa q esto solo no es suficiente
  }
}
