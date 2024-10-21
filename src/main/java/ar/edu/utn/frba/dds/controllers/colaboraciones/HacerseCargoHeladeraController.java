package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ColaboradorPorSession;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import java.time.LocalDateTime;

public class HacerseCargoHeladeraController extends ColaboradorPorSession implements ICrudViewsHandler {

    private HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;
    private HeladeraRepository heladeraRepository;


    public HacerseCargoHeladeraController(HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository,
                                          UsuarioService usuarioService,
                                          ColaboradorService colaboradorService) {

        super(usuarioService, colaboradorService);
        this.hacerseCargoHeladeraRepository = hacerseCargoHeladeraRepository;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        context.render("colaboraciones/encargarse_de_heladera_crear.hbs");
    }

    @Override
    public void save(Context context) {

        Colaborador colaborador = obtenerColaboradorPorSession(context);

        //TODO chequear empty heladeras
        Heladera heladeraACargo = heladeraRepository.buscarPorId(context.formParam("heladera_origen")).get();
        HacerseCargoHeladera hacerseCargoHeladera = HacerseCargoHeladera.por(colaborador, LocalDateTime.now(), heladeraACargo);

        this.hacerseCargoHeladeraRepository.guardar(hacerseCargoHeladera);

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

