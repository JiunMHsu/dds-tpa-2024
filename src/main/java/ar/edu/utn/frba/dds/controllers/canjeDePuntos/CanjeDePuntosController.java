package ar.edu.utn.frba.dds.controllers.canjeDePuntos;

import ar.edu.utn.frba.dds.controllers.colaborador.ColaboradorController;
import ar.edu.utn.frba.dds.controllers.productosServicios.ProductosServiciosController;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.puntosDeColaboracion.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.repositories.canjeDePuntos.CanjeDePuntosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.OfertaDeProductosRepository;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.Optional;

public class CanjeDePuntosController implements ICrudViewsHandler {

    private CanjeDePuntosRepository canjeDePuntosRepository;
    //TODO no se si esta bueno incluir los controller pero por ahora los necesito
    private ColaboradorController colaboradorController;

    private ProductosServiciosController productosServiciosController;



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

        OfertaDeProductos oferta = productosServiciosController.ofertaPorId(context.formParam("oferta_id"));
        CanjeDePuntos canjeDePuntosNuevo = CanjeDePuntos.por(colaboradorCanje, LocalDateTime.now(), puntosCanjeados,puntosRestantes, oferta);

        this.canjeDePuntosRepository.guardar(canjeDePuntosNuevo);

        context.redirect("canjeDePuntos/canje_exitoso.hbs");

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
