package ar.edu.utn.frba.dds.controllers.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.heladera.AperturaHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.RetiroDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.SolicitudDeAperturaRepository;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeladeraController implements ICrudViewsHandler {

    private HeladeraRepository heladeraRepository;
    private RetiroDeViandaRepository retiroDeViandaRepository;
    private SolicitudDeAperturaRepository solicitudDeAperturaRepository;
    private AperturaHeladeraRepository aperturaHeladeraRepository;

    public HeladeraController(HeladeraRepository heladeraRepository,
                              RetiroDeViandaRepository retiroDeViandaRepository,
                              SolicitudDeAperturaRepository solicitudDeAperturaRepository,
                              AperturaHeladeraRepository aperturaHeladeraRepository) {
        this.heladeraRepository = heladeraRepository;
        this.retiroDeViandaRepository = retiroDeViandaRepository;
        this.solicitudDeAperturaRepository = solicitudDeAperturaRepository;
        this.aperturaHeladeraRepository = aperturaHeladeraRepository;
    }

    @Override
    public void index(Context context) {
        List<Heladera> heladeras = this.heladeraRepository.obtenerTodos();

        Map<String, Object> model = new HashMap<>();
        model.put("heladeras", heladeras);
        model.put("titulo", "Listado de heladeras");

        context.render("heladera/heladera.hbs", model);
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

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

}
