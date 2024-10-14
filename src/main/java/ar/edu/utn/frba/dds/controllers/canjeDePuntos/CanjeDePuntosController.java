package ar.edu.utn.frba.dds.controllers.canjeDePuntos;

import ar.edu.utn.frba.dds.controllers.colaborador.ColaboradorController;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.puntosDeColaboracion.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.repositories.canjeDePuntos.CanjeDePuntosRepository;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.Optional;

public class CanjeDePuntosController implements ICrudViewsHandler {

    private CanjeDePuntosRepository canjeDePuntosRepository;
    private ColaboradorController colaboradorController;

    public CanjeDePuntosController(CanjeDePuntosRepository canjeDePuntosRepository) {
        this.canjeDePuntosRepository = canjeDePuntosRepository;
    }
    @Override
    public void index(Context context){

    }

    @Override
    public void show(Context context){

    }

    @Override
    public void create(Context context){
        context.render("canjeDePuntos/canje_crear.hbs");
    }
    @Override
    public void save(Context context){
        //del formulario, me llegan todos los datos del canje?
        //se manda el id del colaborador? es el usuario del perfil
        Colaborador colaboradorCanje = colaboradorController.colaboradorPorId(context.formParam("colaborador_id"));

        Double puntosCanjeados = Double.valueOf(context.formParam("puntos_canjeados"));
        Double puntosRestantes = Double.valueOf(context.formParam("puntos_restantes"));

        //Colaborador colaboradorOferta = colaboradorController;
        //OfertaDeProductos oferta = OfertaDeProductos.por();
        //CanjeDePuntos canjeDePuntosNuevo = CanjeDePuntos.por(colaboradorCanje, LocalDateTime.now(), puntosCanjeados,puntosRestantes);
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
