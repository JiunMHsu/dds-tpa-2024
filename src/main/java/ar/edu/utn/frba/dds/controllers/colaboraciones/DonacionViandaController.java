package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.DonacionViandaDTO;
import ar.edu.utn.frba.dds.exceptions.NonColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Comida;
import ar.edu.utn.frba.dds.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaboraciones.DonacionViandaService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DonacionViandaController extends ColaboradorRequired implements ICrudViewsHandler {

    private final DonacionViandaService donacionViandaService;

    public DonacionViandaController(UsuarioService usuarioService,
                                    ColaboradorService colaboradorService,
                                    DonacionViandaService donacionViandaService) {
        super(usuarioService, colaboradorService);
        this.donacionViandaService = donacionViandaService;
    }

    @Override
    public void index(Context context) {
        // TODO - Implementar
    }

    @Override
    public void show(Context context) { // TODO - Revisar
        String donacionViandaId = context.pathParam("id");
        Optional<DonacionVianda> donacionVianda = donacionViandaService.buscarPorId(donacionViandaId);

        if (donacionVianda.isEmpty())
            throw new ResourceNotFoundException("No se encontró donacion por vianda paraColaborador id " + donacionViandaId);

        Map<String, Object> model = new HashMap<>();

        DonacionViandaDTO donacionViandaDTO = DonacionViandaDTO.completa(donacionVianda.get());
        model.put("donacion_vianda", donacionViandaDTO);

        context.render("colaboraciones/colaboracion_detalle.hbs", model);
    }

    @Override
    public void create(Context context) {
        Colaborador colaborador = colaboradorFromSession(context);

        if (!colaborador.puedeColaborar(TipoColaboracion.DONACION_VIANDAS))
            throw new UnauthorizedException("No tienes permiso");

        render(context, "colaboraciones/donacion_vianda_crear.hbs", new HashMap<>());
    }

    @Override
    public void save(Context context) {

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {
            Colaborador colaborador = colaboradorFromSession(context);

            Comida comida = Comida.with(
                    context.formParamAsClass("comida", String.class).get(),
                    context.formParamAsClass("calorias", Integer.class).get());

            Vianda vianda = Vianda.with(
                    comida,
                    LocalDate.parse(context.formParamAsClass("caducidad", String.class).get()),
                    context.formParamAsClass("peso", Integer.class).get());

            DonacionVianda donacionVianda = DonacionVianda.por(
                    colaborador, LocalDateTime.now(), vianda, false);

            this.donacionViandaService.registrar(donacionVianda);

            // TODO - Delegar a Service??
            colaborador.invalidarPuntos();
            this.colaboradorService.actualizar(colaborador);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/colaboraciones", "Seguir Colaborando"));

        } catch (NonColaboratorException e) {
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
