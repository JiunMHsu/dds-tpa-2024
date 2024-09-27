package ar.edu.utn.frba.dds.controllers.heladera;

import ar.edu.utn.frba.dds.models.repositories.heladera.AperturaHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.RetiroDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.SolicitudDeAperturaRepository;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

public class HeladeraController implements ICrudViewsHandler {

    private HeladeraRepository heladeraRepository;
    private RetiroDeViandaRepository retiroDeViandaRepository;
    private SolicitudDeAperturaRepository solicitudDeAperturaRepository;
    private AperturaHeladeraRepository aperturaHeladeraRepository;

    // TODO - Dependencia a clase concreta o generar interfaces??
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
