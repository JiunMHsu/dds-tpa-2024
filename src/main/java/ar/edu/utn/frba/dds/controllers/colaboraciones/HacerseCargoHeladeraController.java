package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.DonacionViandaDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.HacerseCargoHeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.services.colaboraciones.HacerseCargoHeladeraService;
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

public class HacerseCargoHeladeraController extends ColaboradorPorSession implements ICrudViewsHandler {

    private HacerseCargoHeladeraService hacerseCargoHeladeraService;
    private HeladeraService heladeraService;


    public HacerseCargoHeladeraController(HacerseCargoHeladeraService hacerseCargoHeladeraService,
                                          HeladeraService heladeraService,
                                          UsuarioService usuarioService,
                                          ColaboradorService colaboradorService) {

        super(usuarioService, colaboradorService);
        this.heladeraService = heladeraService;
        this.hacerseCargoHeladeraService = hacerseCargoHeladeraService;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {
        String hacerseCargoHeladeraId = context.pathParam("id");
        Optional<HacerseCargoHeladera> hacerseCargoHeladera = hacerseCargoHeladeraService.buscarPorId(hacerseCargoHeladeraId);

        if (hacerseCargoHeladera.isEmpty())
            throw new ResourceNotFoundException("No se encontró un cargo de heladera con id " + hacerseCargoHeladeraId);

        Map<String, Object> model = new HashMap<>();

        HacerseCargoHeladeraDTO hacerseCargoHeladeraDTO = HacerseCargoHeladeraDTO.completa(hacerseCargoHeladera.get());
        model.put("hacerse_cargo_heladera", hacerseCargoHeladeraDTO);

        context.render("colaboraciones/colaboracion_detalle.hbs", model);
    }

    @Override
    public void create(Context context) {

        Colaborador colaborador = obtenerColaboradorPorSession(context);

        boolean tieneColaboracionDistribucionViandas = colaborador.getFormaDeColaborar()
                .stream()
                .anyMatch(colaboracion -> colaboracion.equals(Colaboracion.HACERSE_CARGO_HELADERA));

        if (!tieneColaboracionDistribucionViandas) {
            throw new UnauthorizedException("No tienes permiso");
        }

        context.render("colaboraciones/encargarse_de_heladera_crear.hbs");

    }

    @Override
    public void save(Context context) {

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        Colaborador colaborador = obtenerColaboradorPorSession(context);

        try {

            Optional<Heladera> heladeraACargo = heladeraService.buscarPorNombre(context.formParamAsClass("heladera", String.class).get());

            if (heladeraACargo.isEmpty()) {
                throw new ResourceNotFoundException("Heladera no Encontrada");
            }

            HacerseCargoHeladera hacerseCargoHeladera = HacerseCargoHeladera.por(colaborador, LocalDateTime.now(), heladeraACargo.get());

            this.hacerseCargoHeladeraService.guardar(hacerseCargoHeladera);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/colaboraciones", "Seguir Colaborando"));

        } catch (ValidationException e) {
            redirectDTOS.add(new RedirectDTO("/colaboraciones", "Reintentar"));
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

