package ar.edu.utn.frba.dds.controllers.personaVulnerable;

import ar.edu.utn.frba.dds.dtos.personaVulnerable.PersonaVulnerableDTO;
import ar.edu.utn.frba.dds.exceptions.PersonaVulnerableNotFoundException;
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

public class PersonaVulnerableController extends ColaboradorRequired implements ICrudViewsHandler {

  private final PersonaVulnerableService personaVulnerableService;
  private final TarjetaPersonaVulnerableService tarjetaPersonaVulnerableService;

  public PersonaVulnerableController(PersonaVulnerableService personaVulnerableService,
                                     TarjetaPersonaVulnerableService tarjetaPersonaVulnerableService,
                                     ColaboradorService colaboradorService,
                                     UsuarioService usuarioService) {

    super(usuarioService, colaboradorService);
    this.personaVulnerableService = personaVulnerableService;
    this.tarjetaPersonaVulnerableService = tarjetaPersonaVulnerableService;
  }

  @Override
  public void index(Context context) {

    List<PersonaVulnerable> personasVulnerables = this.personaVulnerableService.buscarTodosPV();

    List<PersonaVulnerableDTO> personasVulnerablesDTO = personasVulnerables.stream()
        .map(PersonaVulnerableDTO::completa)
        .collect(Collectors.toList());

    Map<String, Object> model = new HashMap<>();
    model.put("personasVulnerables", personasVulnerablesDTO);
    model.put("titulo", "Listado por Personas en Situacion Vulnerable");

    // context.render("/colaboraciones/", model);
  }

  @Override
  public void show(Context context) {

    Optional<PersonaVulnerable> personaVulnerableBuscada = this.personaVulnerableService.buscarPVPorId(context.pathParam("id"));

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

  @Override
  public void edit(Context context) {
    Optional<PersonaVulnerable> personaVulnerableBuscada = this.personaVulnerableService.buscarPVPorId(context.pathParam("id"));

    if (personaVulnerableBuscada.isEmpty()) {
      context.status(404).result("Persona en situación vulnerable no encontrada");
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("personaVulnerable", personaVulnerableBuscada.get());
    model.put("titulo", "Editar Persona Vulnerable");

    context.render("colaboraciones/editar_pv.hbs", model);
  }

  @Override
  public void update(Context context) {
    try {
      Documento documento = Documento.with(
          TipoDocumento.valueOf(context.formParamAsClass("tipo_documento", String.class).get()),
          context.formParamAsClass("nro_documento", String.class).get()
      );

      Direccion direccion = Direccion.with(
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

      this.personaVulnerableService.actualizarPV(context.pathParam("id"), personaVulnerableActualizada);

      context.status(HttpStatus.OK).result("Persona vulnerable actualizada exitosamente");
    } catch (ValidationException e) {
      context.status(HttpStatus.BAD_REQUEST).result("Error en la validación por los datos");
    }
  }


  @Override
  public void delete(Context context) {
    String id = context.pathParam("id");
    try {
      this.tarjetaPersonaVulnerableService.eliminarTarjetaPorPersonaId(id);
      this.personaVulnerableService.eliminarPV(context.pathParam("id"));
      context.status(HttpStatus.OK).result("Persona vulnerable y su tarjeta asociada eliminadas exitosamente");
    } catch (PersonaVulnerableNotFoundException e) {
      context.status(HttpStatus.NOT_FOUND).result(e.getMessage());
    }
  }
}
