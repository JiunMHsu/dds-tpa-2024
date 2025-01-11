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
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.permissions.TecnicoRequired;
import ar.edu.utn.frba.dds.permissions.UserRequired;
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
    render(context, "tecnicos/tecnico.hbs", new HashMap<>());
  }

  @Override
  public void show(Context context) {

    String cuitTecnico = context.pathParam("cuit");
    Optional<Tecnico> tecnicoBuscado = this.tecnicoService.buscarTecnicoPorCuit(cuitTecnico);

    if (tecnicoBuscado.isEmpty()) {
      throw new ResourceNotFoundException("No se encontró tecnico paraColaborador cuit " + cuitTecnico);
    }

    Map<String, Object> model = new HashMap<>();
    TecnicoDTO tecnicoDTO = TecnicoDTO.completa(tecnicoBuscado.get());
    model.put("tecnico", tecnicoDTO);

    render(context, "tecnicos/tecnico_detalle.hbs", model);
  }

  @Override
  public void create(Context context) {
    render(context,"tecnicos/tecnico_crear.hbs", new HashMap<>());
  }

  @Override
  public void save(Context context) { // TODO - Ver desp que matchee paraColaborador las vistas

    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    try {

      Usuario usuario = Usuario.conEmail(context.formParamAsClass("mail", String.class).get());

      String nombre = context.formParamAsClass("nombre", String.class).get();
      String apellido = context.formParamAsClass("apellido", String.class).get();

      Documento documento = Documento.with(
          TipoDocumento.valueOf(context.formParamAsClass("tipo_documento", String.class).get()),
          context.formParamAsClass("nro_documento", String.class).get()
      );

      String cuit = context.formParamAsClass("cuit", String.class).get();

      MedioDeNotificacion medioDeNotificacion = MedioDeNotificacion.valueOf(context.formParamAsClass("medio-notificacion", String.class).get());

      Contacto contacto;

      if (medioDeNotificacion.equals(MedioDeNotificacion.EMAIL)) {
        contacto = Contacto.conEmail(context.formParamAsClass("contacto", String.class).get());
      } else if (medioDeNotificacion.equals(MedioDeNotificacion.WHATSAPP)) {
        contacto = Contacto.conWhatsApp(context.formParamAsClass("contacto", String.class).get());
      } else {
        contacto = Contacto.conTelegram(context.formParamAsClass("contacto", String.class).get());
      }

      Ubicacion ubicacion = new Ubicacion(
          context.formParamAsClass("latitud", Double.class).get(),
          context.formParamAsClass("longitud", Double.class).get()
      );

      Integer radio = context.queryParamAsClass("radio", Integer.class)
          .check(rad -> rad >= 0.0, "el radio debe ser positivo").get();

      Area area = Area.with(
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
