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
import ar.edu.utn.frba.dds.utils.ColaboradorPorSession;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DistribucionViandasController extends ColaboradorPorSession implements ICrudViewsHandler {

    private final DistribucionViandasService distribucionViandasService;
    private final HeladeraService heladeraService;

    public DistribucionViandasController(DistribucionViandasService distribucionViandasService,
                                         UsuarioService usuarioService,
                                         ColaboradorService colaboradorService,
                                         HeladeraService heladeraService) {

        super(usuarioService, colaboradorService);
        this.distribucionViandasService = distribucionViandasService;
        this.heladeraService = heladeraService;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {
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

        try {
            Colaborador colaborador = obtenerColaboradorPorSession(context);

            boolean tieneColaboracion = colaborador.getFormaDeColaborar()
                    .stream()
                    .anyMatch(colaboracion -> colaboracion.equals(TipoColaboracion.DISTRIBUCION_VIANDAS));

            if (!tieneColaboracion) {
                throw new UnauthorizedException("No tienes permiso");
            }

            context.render("colaboraciones/distribucion_viandas_crear.hbs");

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

            Optional<Heladera> optionalHeladeraOrigen = heladeraService.buscarPorNombre(context.formParamAsClass("origen", String.class).get());

            if (optionalHeladeraOrigen.isEmpty()) {
                throw new ResourceNotFoundException("Heladera no Encontrada");
            }

            Optional<Heladera> optionalHeladeraDestino = heladeraService.buscarPorNombre(context.formParamAsClass("destino", String.class).get());

            if (optionalHeladeraDestino.isEmpty()) {
                throw new ResourceNotFoundException("Heladera no Encontrada");
            }

            Integer viandas = Integer.valueOf(context.formParamAsClass("cantidad", Integer.class).get());
            String motivo = context.formParamAsClass("motivo", String.class).get();

            Heladera heladeraOrigen = optionalHeladeraOrigen.get();
            Heladera heladeraDestino = optionalHeladeraDestino.get();

            try {
                heladeraOrigen.quitarViandas(viandas);
                heladeraDestino.agregarViandas(viandas);
            } catch (ExcepcionCantidadDeViandas e) {
                redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
                context.render("post_result.hbs", model);
                return;
            }

            this.heladeraService.actualizarHeladera(heladeraOrigen);
            this.heladeraService.actualizarHeladera(heladeraDestino);

            DistribucionViandas distribucionViandas = DistribucionViandas.por(
                    colaborador,
                    LocalDateTime.now(),
                    heladeraOrigen,
                    heladeraDestino,
                    viandas,
                    motivo
            );

            this.distribucionViandasService.guardar(distribucionViandas);

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

