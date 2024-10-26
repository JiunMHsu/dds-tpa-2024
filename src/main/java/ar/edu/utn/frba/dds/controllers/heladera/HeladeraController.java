package ar.edu.utn.frba.dds.controllers.heladera;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.UbicacionDTO;
import ar.edu.utn.frba.dds.dtos.heladera.HeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RangoTemperatura;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.puntoIdeal.PuntoIdealService;
import ar.edu.utn.frba.dds.utils.IBrokerMessageHandler;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HeladeraController implements ICrudViewsHandler, IBrokerMessageHandler {

    private final HeladeraService heladeraService;
    private final PuntoIdealService puntoIdealService;
    private final IncidenteService incidenteService;

    public HeladeraController(HeladeraService heladeraService,
                              PuntoIdealService puntoIdealService,
                              IncidenteService incidenteService) {
        this.heladeraService = heladeraService;
        this.puntoIdealService = puntoIdealService;
        this.incidenteService = incidenteService;
    }

    @Override
    public void index(Context context) {
        List<Heladera> heladeras = this.heladeraService.buscarTodas();

        List<HeladeraDTO> heladerasDTO = heladeras.stream()
                .map(HeladeraDTO::preview)
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("heladeras", heladerasDTO);

        context.render("heladeras/heladeras.hbs", model);
    }

    @Override
    public void show(Context context) {
        String heladeraId = context.pathParam("id");
        Optional<Heladera> heladera = this.heladeraService.buscarPorId(heladeraId);

        if (heladera.isEmpty())
            throw new ResourceNotFoundException("No se encontró heladera paraColaborador id " + heladeraId);

        Map<String, Object> model = new HashMap<>();

        HeladeraDTO heladeraDTO = HeladeraDTO.completa(heladera.get());
        model.put("heladera", heladeraDTO);

        context.render("heladeras/heladera_detalle.hbs", model);
    }

    @Override
    public void create(Context context) {
        try {
            Double latitud = context.queryParamAsClass("lat", Double.class).get();
            Double longitud = context.queryParamAsClass("lon", Double.class).get();
            Integer radio = context.queryParamAsClass("radio", Integer.class)
                    .check(rad -> rad >= 0.0, "el radio debe ser positivo").get();

            List<UbicacionDTO> ubicaciones = puntoIdealService
                    .obtenerPuntosIdeales(latitud, longitud, radio)
                    .stream().map(UbicacionDTO::fromUbicacion).toList();

            Map<String, Object> model = new HashMap<>();
            model.put("puntosRecomendados", ubicaciones);

            context.render("heladeras/heladera_crear.hbs", model);
        } catch (ValidationException e) {
            context.render("heladeras/heladera_crear.hbs");
        }

    }

    @Override
    public void save(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {
            String nombre = context.formParamAsClass("nombre", String.class).get();
            Integer capacidad = context.formParamAsClass("capacidad", Integer.class)
                    .check(c -> c > 0, "la capacidad debe ser positiva").get();

            Double latitud = context.formParamAsClass("latitud", Double.class).get();
            Double longitud = context.formParamAsClass("longitud", Double.class).get();

            Direccion direccion = Direccion.with(
                    new Barrio(context.formParamAsClass("barrio", String.class).get()),
                    new Calle(context.formParamAsClass("calle", String.class).get()),
                    context.formParamAsClass("altura", Integer.class).get(),
                    new Ubicacion(latitud, longitud)
            );

            Double tempMaxima = context.formParamAsClass("temp_maxima", Double.class).get();
            Double tempMinima = context.formParamAsClass("temp_minima", Double.class).get();
            RangoTemperatura rangoTemperatura = new RangoTemperatura(tempMaxima, tempMinima);

            Heladera heladeraNueva = Heladera.con(nombre, direccion, capacidad, rangoTemperatura);
            this.heladeraService.guardarHeladera(heladeraNueva);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/heladeras", "Ver Heladeras"));

        } catch (ValidationException e) {
            redirectDTOS.add(new RedirectDTO("/heladeras/new", "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }
    }

    @Override
    public void edit(Context context) {
        // TODO - edit
        context.render("heladeras/heladera_editar.hbs");

        // devuelve formulario paraColaborador editar heladera
        // Optional<Heladera> posibleHeladeraBuscada = this.heladeraService.buscarPorId(context.formParam("id"));
        // TODO chequear empty

        // if(posibleHeladeraBuscada.isEmpty()) {
        //     context.status(HttpStatus.NOT_FOUND);
        //     return;
        // }

        // Map<String, Object> model = new HashMap<>();
        // model.put("heladera", posibleHeladeraBuscada.get());
        // model.put("edicion", true);

        // context.render("heladeras/heladera_detalle.hbs", model);
    }

    @Override
    public void update(Context context) {
        // voy a considerar que solo se puede modificar rango por temperatura
        Optional<Heladera> posibleHeladeraActualizar = this.heladeraService.buscarPorId(context.formParam("id"));
        // TODO - chequeo si no existe

        // interpreto que los campos son obligatorios (no pueden ser null)
        Double nuevoMaximo = Double.valueOf(context.formParam("maximo"));
        Double nuevoMinimo = Double.valueOf(context.formParam("minimo"));

        Heladera heladeraActualizada = posibleHeladeraActualizar.get();

        heladeraActualizada.setRangoTemperatura(new RangoTemperatura(nuevoMaximo, nuevoMinimo));

        this.heladeraService.actualizarHeladeras(heladeraActualizada);

        context.status(HttpStatus.OK);
        // mostrar algo por exitoso
    }

    @Override
    public void delete(Context context) {
        Optional<Heladera> posibleHeladeraAEliminar = this.heladeraService.buscarPorId(context.formParam("id"));
        // TODO - chequeo si no existe

        this.heladeraService.eliminarHeladera(posibleHeladeraAEliminar.get());
        context.status(HttpStatus.OK);
        // mostrar algo por exitoso
    }

    @Override
    public void recibirTemperatura(double temperatura) {
        // TODO
    }

    @Override
    public void recibirMovimiento() {
        // TODO
    }

    @Override
    public void recibirCodigoTarjeta(String codigoTarjeta) {
        // TODO
    }

}
