package ar.edu.utn.frba.dds.controllers.suscripcion;

import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.suscripcion.FallaHeladeraService;
import ar.edu.utn.frba.dds.services.suscripcion.FaltaViandaService;
import ar.edu.utn.frba.dds.services.suscripcion.HeladeraLlenaService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

public class SuscripcionHeladeraController implements ICrudViewsHandler {

    private final HeladeraService heladeraService;
    private final FallaHeladeraService fallaHeladeraService;
    private final FaltaViandaService faltaViandaService;
    private final HeladeraLlenaService heladeraLlenaService;

    public SuscripcionHeladeraController (HeladeraService heladeraService,
                                          FallaHeladeraService fallaHeladeraService,
                                          FaltaViandaService faltaViandaService,
                                          HeladeraLlenaService heladeraLlenaService) {

        this.heladeraService = heladeraService;
        this.fallaHeladeraService = fallaHeladeraService;
        this.faltaViandaService = faltaViandaService;
        this.heladeraLlenaService = heladeraLlenaService;
    }

    @Override
    public void index(Context context){

    }
    @Override
    public void show(Context context){

    }
    @Override
    public void create(Context context){

    }
    @Override
    public void save(Context context){

    }
    @Override
    public void edit(Context context){

    }
    @Override
    public void update(Context context){

    }
    @Override
    public void delete(Context context){

    }

}
