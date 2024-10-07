package ar.edu.utn.frba.dds.controllers.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.AperturaHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RetiroDeVianda;
import ar.edu.utn.frba.dds.models.repositories.heladera.IHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.ISolicitudDeAperturaRepository;
import ar.edu.utn.frba.dds.utils.IBrokerMessageHandler;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class HeladeraController implements ICrudViewsHandler, IBrokerMessageHandler {

    IHeladeraRepository heladeraRepository;
    ISolicitudDeAperturaRepository solicitudDeAperturaRepository;
    ICrudRepository<RetiroDeVianda> retiroDeViandaRepository;
    ICrudRepository<AperturaHeladera> aperturaHeladeraRepository;

    public HeladeraController(IHeladeraRepository heladeraRepository,
                              ISolicitudDeAperturaRepository solicitudDeAperturaRepository,
                              ICrudRepository<RetiroDeVianda> retiroDeViandaRepository,
                              ICrudRepository<AperturaHeladera> aperturaHeladeraRepository) {

        this.heladeraRepository = heladeraRepository;
        this.retiroDeViandaRepository = retiroDeViandaRepository;
        this.solicitudDeAperturaRepository = solicitudDeAperturaRepository;
        this.aperturaHeladeraRepository = aperturaHeladeraRepository;
    }

    @Override
    public void index(Context context) {
        List<Heladera> heladeras = this.heladeraRepository.buscarTodos();

        Map<String, Object> model = new HashMap<>();
        model.put("heladeras", heladeras);
        model.put("titulo", "Listado de heladeras");

        context.render("heladera/heladera.hbs", model);
    }

    @Override
    public void show(Context context) {
        //por id
        Optional<Heladera> posibleHeladeraBuscada = this.heladeraRepository.buscarPorId(context.pathParam("id"));

        if (posibleHeladeraBuscada.isEmpty()) {
            context.status(404);//not found
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("heladera", posibleHeladeraBuscada.get());

        context.render("heladera/detalle_heladera.hbs", model);

    }

    @Override
    public void create(Context context) {
        if (false) { // TODO usuario no tiene permiso de admin
            context.status(403).result("No tienes permiso para dar de alta la heladera.");
            return;
        }
        context.render("heladera/formulario_heladera.hbs");
    }

    @Override
    public void save(Context context) {
        Heladera nuevaHeladera = new Heladera();

        nuevaHeladera.setNombre(context.formParam("nombre"));
        //nuevaHeladera.setDireccion(context.formParam("direccion"));
        //nuevaHeladera.setViandas(Integer.valueOf(Objects.requireNonNull(context.formParam("viandas")))); deberia ser cero?
        //nuevaHeladera.setRangoTemperatura(context.formParam("nombre"));
        nuevaHeladera.setInicioFuncionamiento(LocalDateTime.now());
        // deberia usar el builder

        nuevaHeladera.setCapacidad(Integer.valueOf(Objects.requireNonNull(context.formParam("capacidad"))));

        this.heladeraRepository.guardar(nuevaHeladera);
        //O BIEN LANZO UNA PANTALLA DE EXITO
        //O BIEN REDIRECCIONO AL USER A LA PANTALLA DE LISTADO DE PRODUCTOS
        context.redirect("/productos");

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
