package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.services.colaboraciones.DonacionDineroService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ColaboradorPorSession;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class DonacionDineroController extends ColaboradorPorSession implements ICrudViewsHandler {

    private final DonacionDineroService donacionDineroService;

    public DonacionDineroController(DonacionDineroService donacionDineroService,
                                    UsuarioService usuarioService,
                                    ColaboradorService colaboradorService) {

        super(usuarioService, colaboradorService);
        this.donacionDineroService = donacionDineroService;
    }

    @Override
    public void index(Context context) {
    }

    @Override
    public void show(Context context) {
        // TODO
    }

    @Override
    public void create(Context context) {
        context.render("colaboraciones/donacion_dinero_crear.hbs");
    }

    @Override
    public void save(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {
            Colaborador colaborador = obtenerColaboradorPorSession(context);

            Integer monto = context.formParamAsClass("monto", Integer.class).get();

            String unidadTiempo = context.formParamAsClass("unidad_tiempo", String.class).getOrDefault("NONE");
            Integer periocidad = context.formParamAsClass("periocidad", Integer.class)
                    .check(p -> p >= 0, "el periodo debe ser positivo")
                    .getOrDefault(0);

            Period frecuencia = switch (unidadTiempo) {
                case "DAY" -> Period.ofDays(periocidad);
                case "WEEK" -> Period.ofWeeks(periocidad);
                case "MONTH" -> Period.ofMonths(periocidad);
                case "YEAR" -> Period.ofYears(periocidad);
                default -> null;
            };

            // TODO - ver como lanzar y manejar fallas de creación y guardado
            DonacionDinero donacionDinero = DonacionDinero.por(colaborador, LocalDateTime.now(), monto, frecuencia);
            donacionDineroService.registrarDonacion(donacionDinero);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/colaboraciones", "Colaboraciones"));

        } catch (NoSuchElementException e) {
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
