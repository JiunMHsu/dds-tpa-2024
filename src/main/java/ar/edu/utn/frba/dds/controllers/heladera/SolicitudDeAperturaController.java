package ar.edu.utn.frba.dds.controllers.heladera;

import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.SolicitudDeAperturaRepository;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

public class SolicitudDeAperturaController implements ICrudViewsHandler {

    private HeladeraRepository heladeraRepository;
    private SolicitudDeAperturaRepository solicitudDeAperturaRepository;

    public SolicitudDeAperturaController(HeladeraRepository heladeraRepository,
                                         SolicitudDeAperturaRepository solicitudDeAperturaRepository) {
        this.heladeraRepository = heladeraRepository;
        this.solicitudDeAperturaRepository = solicitudDeAperturaRepository;
    }

    @Override
    public void index(Context context) {

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

}
