package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ColaboradorPorSession;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.*;

public class DistribucionViandasController extends ColaboradorPorSession implements ICrudViewsHandler {

    private DistribucionViandasRepository distribucionViandasRepository;
    private HeladeraService heladeraService;
    public DistribucionViandasController(DistribucionViandasRepository distribucionViandasRepository,
                                         UsuarioService usuarioService,
                                         ColaboradorService colaboradorService,
                                         HeladeraService heladeraService) {

        super(usuarioService, colaboradorService);
        this.distribucionViandasRepository = distribucionViandasRepository;
        this.heladeraService = heladeraService;
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

        boolean tieneColaboracionDistribucionViandas = colaborador.getFormaDeColaborar()
                .stream()
                .anyMatch(colaboracion -> colaboracion.equals(Colaboracion.DISTRIBUCION_VIANDAS));

        if (!tieneColaboracionDistribucionViandas) {
            throw new UnauthorizedException("No tienes permiso");
        }

        context.render("colaboraciones/distribucion_viandas_crear.hbs");
    }

    @Override
    public void save(Context context) {

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        Colaborador colaborador = obtenerColaboradorPorSession(context);

        try {

            Optional<Heladera> heladeraOrigen = heladeraService.buscarHeladeraPorNombre(context.formParamAsClass("origen", String.class).get());

            if (heladeraOrigen.isEmpty()) {
                throw new ResourceNotFoundException("Heladera no Encontrada");
            }

            Optional<Heladera> heladeraDestino = heladeraService.buscarHeladeraPorNombre(context.formParamAsClass("destino", String.class).get());

            if (heladeraDestino.isEmpty()) {
                throw new ResourceNotFoundException("Heladera no Encontrada");
            }

            Integer viandas = Integer.valueOf(context.formParamAsClass("cantidad", Integer.class).get());
            String motivo = context.formParamAsClass("motivo", String.class).get();

            DistribucionViandas distribucionViandas = DistribucionViandas.por(
                    colaborador,
                    LocalDateTime.now(),
                    heladeraOrigen.get(),
                    heladeraDestino.get(),
                    viandas,
                    motivo
            );

            this.distribucionViandasRepository.guardar(distribucionViandas);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/colaboraciones", "Seguir Colaborando"));

        } catch (ValidationException e) {
            redirectDTOS.add(new RedirectDTO("/colaboraciones/new", "Reintentar"));
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

