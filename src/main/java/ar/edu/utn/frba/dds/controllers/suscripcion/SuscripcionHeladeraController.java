package ar.edu.utn.frba.dds.controllers.suscripcion;

import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.suscripcion.FallaHeladeraService;
import ar.edu.utn.frba.dds.services.suscripcion.FaltaViandaService;
import ar.edu.utn.frba.dds.services.suscripcion.HeladeraLlenaService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.utils.UserRequired;
import io.javalin.http.Context;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;

public class SuscripcionHeladeraController implements UserRequired {

    private final HeladeraService heladeraService;
    private final FallaHeladeraService fallaHeladeraService;
    private final FaltaViandaService faltaViandaService;
    private final HeladeraLlenaService heladeraLlenaService;

    public SuscripcionHeladeraController(HeladeraService heladeraService,
                                         FallaHeladeraService fallaHeladeraService,
                                         FaltaViandaService faltaViandaService,
                                         HeladeraLlenaService heladeraLlenaService) {
        save();
        this.heladeraService = heladeraService;
        this.fallaHeladeraService = fallaHeladeraService;
        this.faltaViandaService = faltaViandaService;
        this.heladeraLlenaService = heladeraLlenaService;
    }


    public void create(Context context) {

    }

    public void createFallaHeladera(Context context) {

    }

    public void createFaltaVianda(Context context) {

    }

    public void createHeladeraLlena(Context context) {

    }

    public void save(Context context) {

    }


}
