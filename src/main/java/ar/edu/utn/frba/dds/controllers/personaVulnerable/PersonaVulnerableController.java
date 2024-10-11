package ar.edu.utn.frba.dds.controllers.personaVulnerable;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.*;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.services.colaboraciones.RepartoDeTarjetaService;
import ar.edu.utn.frba.dds.services.personaVulnerable.PersonaVulnerableService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.List;

public class PersonaVulnerableController implements ICrudViewsHandler {

    private PersonaVulnerableService personaVulnerableService;
    private RepartoDeTarjetaService repartoDeTarjetaService;

    public PersonaVulnerableController (PersonaVulnerableService personaVulnerableService,
                                        RepartoDeTarjetaService repartoDeTarjetaService) {
        this.personaVulnerableService = personaVulnerableService;
        this.repartoDeTarjetaService = repartoDeTarjetaService;
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

        LocalDate fechaRegistro = LocalDate.now();

        PersonaVulnerable nuevaPV = new PersonaVulnerable(
                context.formParam("nombre"),
                documento,
                LocalDate.parse(context.formParam("fecha_nacimiento")),
                fechaRegistro,
                direccion,
                Integer.valueOf(context.formParam("menores_a_cargo"))
                );

        // TarjetaPersonaVulnerable tarjeta = new TarjetaPersonaVulnerable(context.formParam("codigo_tarjeta")); TODO - Ver error

        Colaborador colaborador = new Colaborador(); // TODO - obtener de la session

        this.personaVulnerableService.guardarPV(nuevaPV);
        this.repartoDeTarjetaService.registrarReparto(colaborador, nuevaPV, tarjeta);
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
