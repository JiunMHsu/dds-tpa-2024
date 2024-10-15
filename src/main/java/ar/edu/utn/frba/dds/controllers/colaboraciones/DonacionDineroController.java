package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.time.Period;
public class DonacionDineroController implements ICrudViewsHandler {

    private DonacionDineroRepository donacionDineroRepository;

    private ColaboradorRepository colaboradorRepository;

    public DonacionDineroController(DonacionDineroRepository donacionDineroRepository) {
        this.donacionDineroRepository = donacionDineroRepository;
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
        Integer monto = Integer.valueOf(context.formParam("monto"));
        Period frecuencia= Period.of(Integer.valueOf(context.formParam("anio")),Integer.valueOf(context.formParam("meses")),Integer.valueOf(context.formParam("dias")) );
        //TODO ver frecuencia period
        DonacionDinero donacionDinero = DonacionDinero.por(colaborador, LocalDateTime.now(), monto, frecuencia);

        this.donacionDineroRepository.guardar(donacionDinero);

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
