package ar.edu.utn.frba.dds.controllers.suscripcion;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.heladera.HeladeraDTO;
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
import java.util.*;

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

        String heladeraId = context.queryParamAsClass("heladera", String.class).get();

        Heladera heladera = this.heladeraService
                .buscarPorId(heladeraId)
                .orElseThrow(ResourceNotFoundException::new);

        Map<String, Object> model = new HashMap<>();

        HeladeraDTO heladeraDTO = HeladeraDTO.completa(heladera);
        model.put("heladera", heladeraDTO);

        render(context,"suscripcion/suscripciones.hbs", model);
    }

    public void createFallaHeladera(Context context) {
        render(context, "suscripcion/suscripcion_falla_heladera.hbs", new HashMap<>());
    }

    public void createFaltaVianda(Context context) {
        render(context, "suscripcion/suscripcion_falta_viandas.hbs", new HashMap<>());
    }

    public void createHeladeraLlena(Context context) {
        render(context, "suscripcion/suscripcion_heladera_llena.hbs", new HashMap<>());
    }

    public void saveFallaHeladera(Context context) {

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {

            Colaborador colaborador = colaboradorFromSession(context);

            String heladeraId = context.queryParamAsClass("id", String.class).get();

            Heladera heladera = this.heladeraService
                    .buscarPorId(heladeraId)
                    .orElseThrow(ResourceNotFoundException::new);

            MedioDeNotificacion medioDeNotificacion = MedioDeNotificacion.valueOf(context.formParamAsClass("medio-notificacion", String.class).get());

            this.fallaHeladeraService.registrar(colaborador, heladera, medioDeNotificacion);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/heladeras", "Ir a Heladeras"));

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

            System.out.println("Colaborador: " + colaborador);

            String heladeraId = context.queryParamAsClass("heladera", String.class).get();

            System.out.println("Id de la heladera: " + heladeraId);

            Heladera heladera = this.heladeraService
                    .buscarPorId(heladeraId)
                    .orElseThrow(ResourceNotFoundException::new);

            System.out.println("Heladera: " + heladera);

            Integer viandasRestantes = context.formParamAsClass("cantidad-viandas", Integer.class).get();

            MedioDeNotificacion medioDeNotificacion = MedioDeNotificacion.valueOf(context.formParamAsClass("medio-notificacion", String.class).get());

            this.faltaViandaService.registrar(colaborador, heladera, medioDeNotificacion, viandasRestantes);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/heladeras", "Ir a Heladeras"));

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

    public void saveHeladeraLlena(Context context) {

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {

            Colaborador colaborador = colaboradorFromSession(context);

            String heladeraId = context.queryParam("heladera");

            Heladera heladera = this.heladeraService
                    .buscarPorId(heladeraId)
                    .orElseThrow(ResourceNotFoundException::new);

            MedioDeNotificacion medioDeNotificacion = MedioDeNotificacion.valueOf(context.formParamAsClass("medio-notificacion", String.class).get());

            Integer espacioRestante = context.formParamAsClass("viandas-restantes", Integer.class).get();

            this.heladeraLlenaService.registrar(colaborador, heladera, medioDeNotificacion, espacioRestante);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/heladeras", "Ir a Heladeras"));

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
