package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.exceptions.NonColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.services.colaboraciones.RepartoDeTarjetaService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.utils.UserRequired;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepartoDeTarjetaController extends UserRequired implements ICrudViewsHandler {

    private final RepartoDeTarjetaService repartoDeTarjetaService;

    public RepartoDeTarjetaController(UsuarioService usuarioService,
                                      ColaboradorService colaboradorService,
                                      RepartoDeTarjetaService repartoDeTarjetaService) {
        super(usuarioService, colaboradorService);
        this.repartoDeTarjetaService = repartoDeTarjetaService;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        Colaborador colaborador = colaboradorFromSession(context);

        if (!colaborador.puedeColaborar(TipoColaboracion.REPARTO_DE_TARJETAS))
            throw new UnauthorizedException("No tiene permiso");

        render(context, "colaboraciones/registro_pv_crear.hbs", new HashMap<>());
    }

    @Override
    public void save(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {
            Colaborador colaborador = colaboradorFromSession(context);

            Documento documento = Documento.with(
                    TipoDocumento.valueOf(context.formParamAsClass("tipo_documento", String.class).getOrDefault(null)),
                    context.formParamAsClass("nro_documento", String.class).getOrDefault(null));

            Direccion direccion = Direccion.formularioPV(
                    new Barrio(context.formParamAsClass("barrio", String.class).get()),
                    new Calle(context.formParamAsClass("calle", String.class).get()),
                    Integer.valueOf(context.formParamAsClass("altura", Integer.class).get())
            );

            PersonaVulnerable nuevaPV = new PersonaVulnerable(
                    context.formParamAsClass("nombre", String.class).get(),
                    documento,
                    LocalDate.parse(context.formParamAsClass("fecha_nacimiento", String.class).get()),
                    LocalDate.now(),
                    direccion,
                    Integer.valueOf(context.formParamAsClass("menores_a_cargo", Integer.class).get())
            );

            // this.personaVulnerableService.guardarPV(nuevaPV);

            // TarjetaPersonaVulnerable tarjeta = this.tarjetaPersonaVulnerableService.registrarTarjetaPV(context.formParamAsClass("tarjeta", String.class).get(), nuevaPV);

            this.repartoDeTarjetaService.registrar(colaborador, nuevaPV, null);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/colaboraciones", "Seguir Colaborando"));

        } catch (ResourceNotFoundException | NonColaboratorException e) {
            throw new UnauthorizedException();
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
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
