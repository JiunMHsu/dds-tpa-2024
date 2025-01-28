package ar.edu.utn.frba.dds.controllers.tecnico;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.TecnicoDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.data.Area;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.stateless.ValidadorDeContrasenias;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.permissions.TecnicoRequired;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class TecnicoController extends TecnicoRequired implements ICrudViewsHandler {

  public TecnicoController(UsuarioService usuarioService,
                           TecnicoService tecnicoService) {
    super(usuarioService, tecnicoService);
  }

  @Override
  public void index(Context context) {
    List<Tecnico> tecnicos = this.tecnicoService.buscarTodos();
    List<TecnicoDTO> tecnicosDTO = tecnicos.stream()
        .map(TecnicoDTO::preview)
        .toList();

    Map<String, Object> model = new HashMap<>();
    model.put("tecnicos", tecnicosDTO);
    render(context, "tecnicos/tecnicos.hbs", model);
  }

  @Override
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

  @Override
  public void create(Context context) {
    render(context, "tecnicos/tecnico_crear.hbs", new HashMap<>());
  }

  @Override
  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      String contrasenia = context.formParamAsClass("clave", String.class).get();
      ValidadorDeContrasenias validador = new ValidadorDeContrasenias();

      if (!validador.esValida(contrasenia)) {
        throw new ar.edu.utn.frba.dds.exceptions.ValidationException("La contraseña no cumple por los requisitos de seguridad.");
      }

      Usuario usuario = Usuario.con(
          context.formParamAsClass("nombre_usuario", String.class).get(),
          contrasenia,
          context.formParamAsClass("email", String.class).get(),
          TipoRol.TECNICO
      );

      String nombre = context.formParamAsClass("nombre", String.class).get();
      String apellido = context.formParamAsClass("apellido", String.class).get();

      Documento documento = Documento.con(
          TipoDocumento.valueOf(context.formParamAsClass("tipo_documento", String.class).get()),
          context.formParamAsClass("nro_documento", String.class).get()
      );

      String cuit = context.formParamAsClass("cuit", String.class).get();

      MedioDeNotificacion medioDeNotificacion = MedioDeNotificacion.valueOf(context.formParamAsClass("medio-notificacion", String.class).get());

      String key = switch (medioDeNotificacion) {
        case EMAIL -> context.formParamAsClass("email", String.class).get();
        case WHATSAPP -> "whatsapp:" + context.formParamAsClass("whatsapp", String.class).get();
        case TELEGRAM -> context.formParamAsClass("telegram", String.class).get();
        case TELEFONO -> context.formParamAsClass("telefono", String.class).get();
      };

      Contacto contacto = Contacto.con(medioDeNotificacion, key);

      Ubicacion ubicacion = new Ubicacion(
          context.formParamAsClass("latitud", Double.class).get(),
          context.formParamAsClass("longitud", Double.class).get()
      );

      Integer radio = context.queryParamAsClass("radio", Integer.class)
          .check(rad -> rad >= 0.0, "el radio debe ser positivo").get();

      Area area = Area.con(
          ubicacion,
          radio,
          new Barrio(context.formParamAsClass("barrio", String.class).get())
      );

      Tecnico tecnico = Tecnico.con(
          usuario,
          nombre,
          apellido,
          documento,
          cuit,
          contacto,
          medioDeNotificacion,
          area
      );

      this.tecnicoService.guardarTecnico(tecnico);

      operationSuccess = true;
      redirectDTOS.add(new RedirectDTO("/home", "Siguiente"));

    } catch (ValidationException e) {
      redirectDTOS.add(new RedirectDTO("/home", "Reintentar"));
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

    String userId = context.sessionAttribute("userId");

    Optional<Usuario> usuarioSession = this.usuarioService.obtenerUsuarioPorID(userId);
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

  @Override
  public void delete(Context context) {

  }
}
