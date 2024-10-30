package ar.edu.utn.frba.dds.controllers.suscripcion;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.exceptions.*;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.suscripcion.FallaHeladeraService;
import ar.edu.utn.frba.dds.services.suscripcion.FaltaViandaService;
import ar.edu.utn.frba.dds.services.suscripcion.HeladeraLlenaService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.utils.UserRequired;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuscripcionHeladeraController extends UserRequired {

    private final HeladeraService heladeraService;
    private final FallaHeladeraService fallaHeladeraService;
    private final FaltaViandaService faltaViandaService;
    private final HeladeraLlenaService heladeraLlenaService;

    public SuscripcionHeladeraController(HeladeraService heladeraService,
                                         FallaHeladeraService fallaHeladeraService,
                                         FaltaViandaService faltaViandaService,
                                         HeladeraLlenaService heladeraLlenaService,
                                         UsuarioService usuarioService,
                                         ColaboradorService colaboradorService) {
        super(usuarioService, colaboradorService);
        this.heladeraService = heladeraService;
        this.fallaHeladeraService = fallaHeladeraService;
        this.faltaViandaService = faltaViandaService;
        this.heladeraLlenaService = heladeraLlenaService;
    }



    public void create(Context context) {

    }

    public void createFallaHeladera(Context context) { render(context, "suscripciones/suscripcion_falla_heladera", new HashMap<>()); }

    public void createFaltaVianda(Context context) { render(context, "suscripciones/suscripcion_falta_vianda", new HashMap<>()); }

    public void createHeladeraLlena(Context context) { render(context, "suscripciones/suscripcion_heladera_llena", new HashMap<>()); }

    public void saveFallaHeladera(Context context) {

        // TODO - habiamos quedado que con solo dar el boton c efectuaba, entonces no deberia recibir nada x formulario

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {

            Colaborador colaborador = colaboradorFromSession(context);

            Heladera heladera = heladeraService
                    .buscarPorNombre(context.formParamAsClass("heladera", String.class).get())
                    .orElseThrow(ResourceNotFoundException::new);

            MedioDeNotificacion medioDeNotificacion = MedioDeNotificacion.valueOf(context.formParamAsClass("medio", String.class).get());

            this.fallaHeladeraService.registrar(colaborador, heladera, medioDeNotificacion);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/suscripciones", "Ir a suscripciones"));

        } catch (NonColaboratorException e) {
            throw new UnauthorizedException(e.getMessage());
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }
    }

    public void saveFaltaVianda(Context context) { // TODO - ver que matchee con las vistas y excepciones


        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {

            Colaborador colaborador = colaboradorFromSession(context);

            // TODO - me parece que no deberia haber un campo Heladera en el formulario, sino que se obtenia directamente
            Heladera heladera = heladeraService
                    .buscarPorNombre(context.formParamAsClass("heladera", String.class).get())
                    .orElseThrow(ResourceNotFoundException::new);

            MedioDeNotificacion medioDeNotificacion = MedioDeNotificacion.valueOf(context.formParamAsClass("medio", String.class).get());

            Integer viandasRestantes = context.formParamAsClass("viandas", Integer.class).get();

            this.faltaViandaService.registrar(colaborador, heladera, medioDeNotificacion, viandasRestantes);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/suscripciones", "Ir a suscripciones"));

        } catch (NonColaboratorException e) {
            throw new UnauthorizedException(e.getMessage());
        } catch (ValidationException | ResourceNotFoundException | SuscripcionFaltaViandaException e) {
            redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }
    }

    public void saveHeladeraLlena(Context context) { // TODO - ver que matchee con las vistas y excepciones

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {

            Colaborador colaborador = colaboradorFromSession(context);

            // TODO - idem que Falta Vianda
            Heladera heladera = heladeraService
                    .buscarPorNombre(context.formParamAsClass("heladera", String.class).get())
                    .orElseThrow(ResourceNotFoundException::new);

            MedioDeNotificacion medioDeNotificacion = MedioDeNotificacion.valueOf(context.formParamAsClass("medio", String.class).get());

            Integer espacioRestante = context.formParamAsClass("espacio-restante", Integer.class).get();

            this.heladeraLlenaService.registrar(colaborador, heladera, medioDeNotificacion, espacioRestante);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/suscripciones", "Ir a suscripciones"));

        } catch (NonColaboratorException e) {
            throw new UnauthorizedException(e.getMessage());
        } catch (ValidationException | ResourceNotFoundException | SuscripcionHeladeraLlenaException e) {
            redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }
    }

}
