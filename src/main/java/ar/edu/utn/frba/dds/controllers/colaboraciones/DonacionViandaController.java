package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Comida;
import ar.edu.utn.frba.dds.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.vianda.ViandaRepository;
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

public class DonacionViandaController extends ColaboradorPorSession implements ICrudViewsHandler {

    private DonacionViandaRepository donacionViandaRepository;
    private ViandaRepository viandaRepository;

    public DonacionViandaController(DonacionViandaRepository donacionViandaRepository,
                                    ViandaRepository viandaRepository,
                                    UsuarioService usuarioService,
                                    ColaboradorService colaboradorService) {
        super(usuarioService, colaboradorService);
        this.donacionViandaRepository = donacionViandaRepository;
        this.viandaRepository = viandaRepository;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

        Colaborador colaborador = obtenerColaboradorPorSession(context);

        boolean tieneColaboracionReparto = colaborador.getFormaDeColaborar()
                .stream()
                .anyMatch(colaboracion -> colaboracion.equals(Colaboracion.DONACION_VIANDAS));

        if (!tieneColaboracionReparto) {
            throw new UnauthorizedException("No tienes permiso");
        }

        context.render("colaboraciones/donacion_vianda_crear.hbs");
    }

    @Override
    public void save(Context context) {

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        Colaborador colaborador = obtenerColaboradorPorSession(context);

        try {

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

            this.donacionViandaRepository.guardar(donacionVianda);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/colaboraciones", "Seguir Colaborando"));

        } catch (ValidationException e) {
            redirectDTOS.add(new RedirectDTO("/colaboraciones", "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }

//        Comida comida = new Comida(context.formParam("nombre_comida"), Integer.valueOf(context.formParam("calorias")));
//        //TODO ver como hacer lo de las fechas
//        LocalDate fechaCaducidad = LocalDate.now();
//        Integer peso = Integer.valueOf(context.formParam("peso"));
//        Vianda vianda = new Vianda(comida, fechaCaducidad, peso);
//        DonacionVianda donacionVianda = DonacionVianda.por(colaborador, LocalDateTime.now(), vianda, false);
//        context.redirect("result_form");

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
