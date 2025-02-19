package ar.edu.utn.frba.dds.controllers.personaVulnerable;

import ar.edu.utn.frba.dds.dtos.personaVulnerable.PersonaVulnerableDTO;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.personaVulnerable.PersonaVulnerableService;
import ar.edu.utn.frba.dds.services.tarjeta.TarjetaPersonaVulnerableService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador para manejar las operaciones relacionadas con personas vulnerables.
 */
public class PersonaVulnerableController extends ColaboradorRequired implements ICrudViewsHandler {

  private final PersonaVulnerableService personaVulnerableService;
  private final TarjetaPersonaVulnerableService tarjetaPersonaVulnerableService;

  /**
   * Constructor para inicializar el controlador de personas vulnerables.
   *
   * @param personaVulnerableService     El servicio de personas vulnerables.
   * @param tarjPersonaVulnerableService El servicio de tarjetas de personas vulnerables.
   * @param colaboradorService           El servicio de colaboradores.
   * @param usuarioService               El servicio de usuarios.
   */
  public PersonaVulnerableController(PersonaVulnerableService personaVulnerableService,
                                     TarjetaPersonaVulnerableService tarjPersonaVulnerableService,
                                     ColaboradorService colaboradorService,
                                     UsuarioService usuarioService) {

    super(usuarioService, colaboradorService);
    this.personaVulnerableService = personaVulnerableService;
    this.tarjetaPersonaVulnerableService = tarjPersonaVulnerableService;
  }

  /**
   * Maneja la visualización del listado de personas vulnerables.
   *
   * @param context El contexto de la solicitud actual.
   */
  @Override
  public void index(Context context) {

    List<PersonaVulnerable> personasVulnerables = this.personaVulnerableService.buscarTodos();

    List<PersonaVulnerableDTO> personasVulnerablesDto = personasVulnerables.stream()
        .map(PersonaVulnerableDTO::fromPersonaVulnerable)
        .collect(Collectors.toList());

    Map<String, Object> model = new HashMap<>();
    model.put("personasVulnerables", personasVulnerablesDto);
    model.put("titulo", "Listado de Personas en Situacion Vulnerable");
    //TODO que onda esto comentado?
    // render(context, "/colaboraciones/", model);
  }

  /**
   * Maneja la visualización de los detalles de una persona vulnerable específica.
   *
   * @param context El contexto de la solicitud actual.
   */
  @Override
  public void show(Context context) {

    Optional<PersonaVulnerable> personaVulnerableBuscada = this.personaVulnerableService
        .buscarPorId(context.pathParam("id"));

    if (personaVulnerableBuscada.isEmpty()) {
      context.status(404).result("Persona en situacion vulnerable no encontrada");
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("Personas Vulnerable", personaVulnerableBuscada.get());

    // context.render("/colaboraciones/", model);
  }

  @Override
  public void create(Context context) {
  }

  @Override
  public void save(Context context) {
  }

  /**
   * Maneja la visualización del formulario de edición de una persona vulnerable existente.
   *
   * @param context El contexto de la solicitud actual.
   */
  @Override
  public void edit(Context context) {
    Optional<PersonaVulnerable> personaVulnerableBuscada = this.personaVulnerableService
        .buscarPorId(context.pathParam("id"));

    if (personaVulnerableBuscada.isEmpty()) {
      context.status(404).result("Persona en situación vulnerable no encontrada");
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("personaVulnerable", personaVulnerableBuscada.get());
    model.put("titulo", "Editar Persona Vulnerable");

    context.render("colaboraciones/editar_pv.hbs", model);
  }

  /**
   * Actualiza los datos de una persona vulnerable existente en el sistema.
   *
   * @param context El contexto de la solicitud actual.
   */
  @Override
  public void update(Context context) {
    try {
      Documento documento = Documento.con(
          TipoDocumento.valueOf(context.formParamAsClass("tipo_documento", String.class)
              .get()),
          context.formParamAsClass("nro_documento", String.class).get()
      );

      Direccion direccion = Direccion.con(
          new Barrio(context.formParamAsClass("barrio", String.class).get()),
          new Calle(context.formParamAsClass("calle", String.class).get()),
          context.formParamAsClass("altura", Integer.class).get()
      );

      PersonaVulnerable personaVulnerableActualizada = new PersonaVulnerable(
          context.formParamAsClass("nombre", String.class).get(),
          documento,
          LocalDate.parse(context.formParamAsClass("fecha_nacimiento", String.class).get()),
          LocalDate.now(),
          direccion,
          context.formParamAsClass("menores_a_cargo", Integer.class).get()
      );

      this.personaVulnerableService.actualizarPersonaVulnerable(context.pathParam("id"),
          personaVulnerableActualizada);

      context.status(HttpStatus.OK)
          .result("Persona vulnerable actualizada exitosamente");
    } catch (ValidationException e) {
      context.status(HttpStatus.BAD_REQUEST)
          .result("Error en la validación nueva los datos");
    }
  }

  @Override
  public void delete(Context context) {
  }
}
