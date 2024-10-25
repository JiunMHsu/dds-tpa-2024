package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.DonacionViandaDTO;
import ar.edu.utn.frba.dds.exceptions.NonColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Comida;
import ar.edu.utn.frba.dds.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.models.repositories.vianda.ViandaRepository;
import ar.edu.utn.frba.dds.services.colaboraciones.DonacionViandaService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ColaboradorPorSession;
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

public class DonacionViandaController extends ColaboradorPorSession implements ICrudViewsHandler {

    private DonacionViandaService donacionViandaService;
    private ViandaRepository viandaRepository;

    public DonacionViandaController(DonacionViandaService donacionViandaService,
                                    ViandaRepository viandaRepository,
                                    UsuarioService usuarioService,
                                    ColaboradorService colaboradorService) {
        super(usuarioService, colaboradorService);
        this.donacionViandaService = donacionViandaService;
        this.viandaRepository = viandaRepository;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {
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

        try {
            Colaborador colaborador = obtenerColaboradorPorSession(context);

            boolean tieneColaboracion = colaborador.getFormaDeColaborar()
                    .stream()
                    .anyMatch(colaboracion -> colaboracion.equals(Colaboracion.DONACION_VIANDAS));

            if (!tieneColaboracion) {
                throw new UnauthorizedException("No tienes permiso");
            }

            context.render("colaboraciones/donacion_vianda_crear.hbs");

        } catch (ResourceNotFoundException | NonColaboratorException e) {
            throw new UnauthorizedException();
        }
    }

    @Override
    public void save(Context context) {

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {

            Colaborador colaborador = obtenerColaboradorPorSession(context);

            Comida comida = Comida.with(
                    context.formParamAsClass("comida", String.class).get(),
                    Integer.valueOf(context.formParamAsClass("calorias", Integer.class).get())
            );

            Vianda vianda = Vianda.with(
                    comida,
                    LocalDate.parse(context.formParamAsClass("caducidad", String.class).get()),
                    Integer.valueOf(context.formParamAsClass("peso", Integer.class).get())
            );

            this.viandaRepository.guardar(vianda);

            DonacionVianda donacionVianda = DonacionVianda.por(
                    colaborador,
                    LocalDateTime.now(),
                    vianda,
                    false
            );

            this.donacionViandaService.guardar(donacionVianda);

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
