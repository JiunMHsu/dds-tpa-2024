package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ColaboradorPorSession;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import java.time.LocalDateTime;

public class DistribucionViandasController extends ColaboradorPorSession implements ICrudViewsHandler {

    private DistribucionViandasRepository distribucionViandasRepository;
    private HeladeraRepository heladeraRepository;

    public DistribucionViandasController(DistribucionViandasRepository distribucionViandasRepository,
                                         UsuarioService usuarioService,
                                         ColaboradorService colaboradorService) {

        super(usuarioService, colaboradorService);
        this.distribucionViandasRepository = distribucionViandasRepository;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        context.render("colaboraciones/distribucion_viandas_crear.hbs");
    }

    @Override
    public void save(Context context) {

        Colaborador colaborador = obtenerColaboradorPorSession(context);

        //TODO chequear empty heladeras
        Heladera heladeraOrigen = heladeraRepository.buscarPorId(context.formParam("heladera_origen")).get();
        Heladera heladeraDestino = heladeraRepository.buscarPorId(context.formParam("heladera_destino")).get();
        Integer viandas = Integer.valueOf(context.formParam("viandas"));
        String motivo = context.formParam("motivo");
        DistribucionViandas distribucionViandas = DistribucionViandas.por(colaborador, LocalDateTime.now(), heladeraOrigen, heladeraDestino, viandas, motivo);

        this.distribucionViandasRepository.guardar(distribucionViandas);

        context.redirect("result_form");

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

