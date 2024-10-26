package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.DistribucionViandasDTO;
import ar.edu.utn.frba.dds.exceptions.NonColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.ExcepcionCantidadDeViandas;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.services.colaboraciones.DistribucionViandasService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.utils.UserRequired;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DistribucionViandasController extends UserRequired implements ICrudViewsHandler {

    private final DistribucionViandasService distribucionViandasService;
    private final HeladeraService heladeraService;

    public DistribucionViandasController(UsuarioService usuarioService,
                                         ColaboradorService colaboradorService,
                                         DistribucionViandasService distribucionViandasService,
                                         HeladeraService heladeraService) {

        super(usuarioService, colaboradorService);
        this.distribucionViandasService = distribucionViandasService;
        this.heladeraService = heladeraService;
    }

    @Override
    public void index(Context context) {
        // TODO: Implementar
    }

    @Override
    public void show(Context context) { // TODO - Revisar
        String distribucionViandasId = context.pathParam("id");
        Optional<DistribucionViandas> distribucionViandas = distribucionViandasService.buscarPorId(distribucionViandasId);

        if (distribucionViandas.isEmpty())
            throw new ResourceNotFoundException("No se encontró distribucion por viandas paraColaborador id " + distribucionViandasId);

        Map<String, Object> model = new HashMap<>();

        DistribucionViandasDTO distribucionViandasDTO = DistribucionViandasDTO.completa(distribucionViandas.get());
        model.put("distribucion_viandas", distribucionViandasDTO);

        context.render("colaboraciones/colaboracion_detalle.hbs", model);
    }

    @Override
    public void create(Context context) {
        Colaborador colaborador = colaboradorFromSession(context);

        if (!colaborador.puedeColaborar(TipoColaboracion.DISTRIBUCION_VIANDAS))
            throw new UnauthorizedException("No tienes permiso");

        render(context, "colaboraciones/distribucion_viandas_crear.hbs", new HashMap<>());
    }

    @Override
    public void save(Context context) {

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {
            Colaborador colaborador = colaboradorFromSession(context);

            Heladera heladeraOrigen = heladeraService
                    .buscarPorNombre(context.formParamAsClass("origen", String.class).get())
                    .orElseThrow(ResourceNotFoundException::new);

            Heladera heladeraDestino = heladeraService
                    .buscarPorNombre(context.formParamAsClass("destino", String.class).get())
                    .orElseThrow(ResourceNotFoundException::new);

            Integer viandas = context.formParamAsClass("cantidad", Integer.class).get();
            String motivo = context.formParamAsClass("motivo", String.class).get();

            DistribucionViandas distribucionViandas = DistribucionViandas.por(
                    colaborador, LocalDateTime.now(), heladeraOrigen, heladeraDestino, viandas, motivo
            );

            this.distribucionViandasService.registrar(distribucionViandas);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/colaboraciones", "Seguir Colaborando"));

        } catch (NonColaboratorException e) {
            throw new UnauthorizedException(e.getMessage());
        } catch (ValidationException | ResourceNotFoundException | ExcepcionCantidadDeViandas e) {
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

