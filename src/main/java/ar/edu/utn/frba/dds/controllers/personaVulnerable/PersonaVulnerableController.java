package ar.edu.utn.frba.dds.controllers.personaVulnerable;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.*;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.services.colaboraciones.RepartoDeTarjetaService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.personaVulnerable.PersonaVulnerableService;
import ar.edu.utn.frba.dds.services.session.SessionService;
import ar.edu.utn.frba.dds.services.tarjeta.TarjetaPersonaVulnerableService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PersonaVulnerableController implements ICrudViewsHandler {

    private PersonaVulnerableService personaVulnerableService;
    private RepartoDeTarjetaService repartoDeTarjetaService;
    private TarjetaPersonaVulnerableService tarjetaPersonaVulnerableService;
    private ColaboradorService colaboradorService;
    private SessionService sessionService;


    public PersonaVulnerableController (PersonaVulnerableService personaVulnerableService,
                                        RepartoDeTarjetaService repartoDeTarjetaService,
                                        TarjetaPersonaVulnerableService tarjetaPersonaVulnerableService,
                                        ColaboradorService colaboradorService,
                                        SessionService sessionService) {
        this.personaVulnerableService = personaVulnerableService;
        this.repartoDeTarjetaService = repartoDeTarjetaService;
        this.tarjetaPersonaVulnerableService = tarjetaPersonaVulnerableService;
        this.colaboradorService = colaboradorService;
        this.sessionService = sessionService;
    }

    @Override
    public void index(Context context) {
        // TODO - viendo cosas del Service
    }

    @Override
    public void show(Context context) {
        // TODO - patenado
    }

    @Override
    public void create(Context context) {
        // TODO - patenado

        context.redirect("/colaboraciones/registro_pv_crear.hbs");
    }

    @Override
    public void save(Context context) {

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

        Optional<String> colaboradorIdSession = sessionService.obtenerColaboradorID(context);
        if (colaboradorIdSession.isEmpty()) {
            context.status(401).result("No autorizado");
            return;
        }

        String colaboradorId = colaboradorIdSession.get();
        Optional<Colaborador> colaboradorSession = colaboradorService.obtenerColaborador(colaboradorId);
        if (colaboradorSession.isEmpty()) {
            context.status(404).result("Colaborador no encontrado");
            return;
        }

        Colaborador colaborador = colaboradorSession.get();

        this.personaVulnerableService.guardarPV(nuevaPV);
        this.repartoDeTarjetaService.registrarReparto(colaborador, nuevaPV, tarjeta);

        // context.redirect("/colaboraciones/"); TODO - ver a donde redireccionar
    }

    @Override
    public void edit(Context context) {
        // TODO - patenado
    }

    @Override
    public void update(Context context) {
        // TODO - patenado
    }

    @Override
    public void delete(Context context) {
        // TODO - patenado
    }
}
