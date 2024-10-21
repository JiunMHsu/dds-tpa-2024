package ar.edu.utn.frba.dds.controllers.personaVulnerable;

import ar.edu.utn.frba.dds.dtos.personaVulnerable.PersonaVulnerableDTO;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.*;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.services.colaboraciones.RepartoDeTarjetaService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.personaVulnerable.PersonaVulnerableService;
import ar.edu.utn.frba.dds.services.tarjeta.TarjetaPersonaVulnerableService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ColaboradorPorSession;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UnauthorizedResponse;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonaVulnerableController extends ColaboradorPorSession implements ICrudViewsHandler {

    private PersonaVulnerableService personaVulnerableService;
    private RepartoDeTarjetaService repartoDeTarjetaService;
    private TarjetaPersonaVulnerableService tarjetaPersonaVulnerableService;

    public PersonaVulnerableController(PersonaVulnerableService personaVulnerableService,
                                       RepartoDeTarjetaService repartoDeTarjetaService,
                                       TarjetaPersonaVulnerableService tarjetaPersonaVulnerableService,
                                       ColaboradorService colaboradorService,
                                       UsuarioService usuarioService) {

        super(usuarioService, colaboradorService);
        this.personaVulnerableService = personaVulnerableService;
        this.repartoDeTarjetaService = repartoDeTarjetaService;
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
        model.put("titulo", "Listado de Personas en Situacion Vulnerable");

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

        Colaborador colaborador = obtenerColaboradorPorSession(context);

        boolean tieneColaboracionReparto = colaborador.getFormaDeColaborar()
                .stream()
                .anyMatch(colaboracion -> colaboracion.equals(Colaboracion.REPARTO_DE_TARJETAS));

        if (!tieneColaboracionReparto) {
            throw new UnauthorizedException("No tienes permiso");
        }

        context.render("colaboraciones/registro_pv_crear.hbs");
    }

    @Override
    public void save(Context context) {

        Colaborador colaborador = obtenerColaboradorPorSession(context);

        Documento documento = Documento.with(
                TipoDocumento.valueOf(context.formParam("tipo_documento")),
                context.formParam("documento")
        );

        Direccion direccion = Direccion.with(
                new Barrio(context.formParam("barrio")),
                new Calle(context.formParam("calle")),
                Integer.valueOf(context.formParam("altura")),
                new Ubicacion(Double.valueOf(context.formParam("latitud")), Double.valueOf(context.formParam("longitud")))
        );

        PersonaVulnerable nuevaPV = new PersonaVulnerable(
                context.formParam("nombre"),
                documento,
                LocalDate.parse(context.formParam("fecha_nacimiento")),
                LocalDate.now(),
                direccion,
                Integer.valueOf(context.formParam("menores_a_cargo"))
                );

        TarjetaPersonaVulnerable tarjeta = this.tarjetaPersonaVulnerableService.registrarTarjetaPV(context.formParam("tarjeta"), nuevaPV); // delege la instanciacion

        this.personaVulnerableService.guardarPV(nuevaPV);
        this.repartoDeTarjetaService.registrarReparto(colaborador, nuevaPV, tarjeta);

        // context.redirect("/colaboraciones/");
    }

    @Override
    public void edit(Context context) {
        // TODO - patenado
    }

    @Override
    public void update(Context context) {
        // TODO - patenado
        // que cosas se podrian modificar?
    }

    @Override
    public void delete(Context context) {

        this.personaVulnerableService.eliminarPV(context.pathParam("id"));
        context.status(HttpStatus.OK);

        // TODO - seguramente haciendo una exception personalizada es mejor
    }
}
