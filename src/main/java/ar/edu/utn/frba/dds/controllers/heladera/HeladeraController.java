package ar.edu.utn.frba.dds.controllers.heladera;

import ar.edu.utn.frba.dds.dtos.heladera.HeladeraDTO;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RangoTemperatura;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.utils.IBrokerMessageHandler;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HeladeraController implements ICrudViewsHandler, IBrokerMessageHandler {

    HeladeraService heladeraService;

    public HeladeraController(HeladeraService heladeraService) {
        this.heladeraService = heladeraService;
    }

    @Override
    public void index(Context context) {
        List<Heladera> heladeras = this.heladeraService.buscarTodas();

        List<HeladeraDTO> heladerasDTO = heladeras.stream()
                .map(HeladeraDTO::preview)
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("heladeras", heladerasDTO);
        model.put("userRol", "Listado de heladeras");

        context.render("heladeras/heladeras.hbs", model);
    }

    @Override
    public void show(Context context) {
        //por id
        Optional<Heladera> posibleHeladeraBuscada = this.heladeraService.buscarPorId(context.formParam("id"));

        if (posibleHeladeraBuscada.isEmpty()) {
            context.status(404);//not found
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("heladera", posibleHeladeraBuscada.get());

        context.render("heladeras/heldadera_detalle.hbs", model);

    }

    @Override
    public void create(Context context) {
        if (false) { // TODO usuario no tiene permiso de admin
            context.status(403).result("No tienes permiso para dar de alta la heladera.");
            return;
        }
        context.render("heladeras/heladera_crear.hbs");
    }

    @Override
    public void save(Context context) {

        Direccion direccion = Direccion.with(
                new Barrio(context.formParam("barrio")),
                new Calle(context.formParam("calle")),
                Integer.valueOf(context.formParam("altura")),
                new Ubicacion(Double.valueOf(context.formParam("latitud")), Double.valueOf(context.formParam("longitud")))
        );
        RangoTemperatura rangoTemperatura = new RangoTemperatura(Double.valueOf(context.formParam("maxima")), Double.valueOf(context.formParam("minima")));
        Heladera nuevaHeladera = Heladera.con(context.formParam("nombre"), direccion, Integer.valueOf(context.formParam("capacidad")), rangoTemperatura, Integer.valueOf(context.formParam("viandas")));
        this.heladeraService.guardarHeladera(nuevaHeladera);
        //O BIEN LANZO UNA PANTALLA DE EXITO
        //O BIEN REDIRECCIONO AL USER A LA PANTALLA DE LISTADO DE PRODUCTOS
        context.redirect("/heladeras/heladeras.hbs"); //redirecciono a heladeras, pero no se si me gusta

    }

    @Override
    public void edit(Context context) {
        // devuelve formulario para editar heladera
        Optional<Heladera> posibleHeladeraBuscada = this.heladeraService.buscarPorId(context.formParam("id"));
        // TODO chequear empty

        // if(posibleHeladeraBuscada.isEmpty()) {
        //     context.status(HttpStatus.NOT_FOUND);
        //     return;
        // }

        Map<String, Object> model = new HashMap<>();
        model.put("heladera", posibleHeladeraBuscada.get());
        model.put("edicion", true);

        context.render("heladeras/heladera_editar.hbs", model);
    }

    @Override
    public void update(Context context) {
        // voy a considerar que solo se puede modificar rango de temperatura
        Optional<Heladera> posibleHeladeraActualizar = this.heladeraService.buscarPorId(context.formParam("id"));
        // TODO - chequeo si no existe

        // interpreto que los campos son obligatorios (no pueden ser null)
        Double nuevoMaximo = Double.valueOf(context.formParam("maximo"));
        Double nuevoMinimo = Double.valueOf(context.formParam("minimo"));

        Heladera heladeraActualizada = posibleHeladeraActualizar.get();

        heladeraActualizada.setRangoTemperatura(new RangoTemperatura(nuevoMaximo, nuevoMinimo));

        this.heladeraService.actualizarHeladera(heladeraActualizada);

        context.status(HttpStatus.OK);
        // mostrar algo de exitoso
    }

    @Override
    public void delete(Context context) {
        Optional<Heladera> posibleHeladeraAEliminar = this.heladeraService.buscarPorId(context.formParam("id"));
        // TODO - chequeo si no existe

        this.heladeraService.eliminarHeladera(posibleHeladeraAEliminar.get());
        context.status(HttpStatus.OK);
        // mostrar algo de exitoso
    }

    // métodos para manejar mensaje de los sensores

    @Override
    public void recibirTemperatura(double temperatura) {

    }

    @Override
    public void recibirMovimiento() {

    }

    @Override
    public void recibirCodigoTarjeta(String codigoTarjeta) {

    }

}
