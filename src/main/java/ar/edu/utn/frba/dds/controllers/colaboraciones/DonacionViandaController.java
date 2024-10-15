package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Comida;
import ar.edu.utn.frba.dds.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class DonacionViandaController implements ICrudViewsHandler {

    private DonacionViandaRepository donacionViandaRepository;
    private ColaboradorRepository colaboradorRepository;


    public DonacionViandaController(DonacionViandaRepository donacionViandaRepository) {
        this.donacionViandaRepository = donacionViandaRepository;
    }

    @Override
    public void index(Context context){

    }
    @Override
    public void show(Context context){

    }
    @Override
    public void create(Context context){
        context.render("colaboraciones/donacion_dinero_crear.hbs");
    }
    @Override
    public void save(Context context){
        Colaborador colaborador = colaboradorRepository.buscarPorId(context.sessionAttribute("userId")).get();
        Comida comida = new Comida(context.formParam("nombre_comida"),Integer.valueOf(context.formParam("calorias")) );
        //TODO ver como hacer lo de las fechas
        LocalDate fechaCaducidad = LocalDate.now();
        Integer peso = Integer.valueOf(context.formParam("peso"));
        Vianda vianda = new Vianda(comida,fechaCaducidad,peso);
        DonacionVianda donacionVianda = DonacionVianda.por(colaborador, LocalDateTime.now(),vianda , false);

        this.donacionViandaRepository.guardar(donacionVianda);

        context.redirect("result_form");

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
