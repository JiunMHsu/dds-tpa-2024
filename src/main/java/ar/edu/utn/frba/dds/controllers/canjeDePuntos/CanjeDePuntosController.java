package ar.edu.utn.frba.dds.controllers.canjeDePuntos;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.puntosPorColaborador.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.entities.puntosPorColaborador.PuntosPorColaborador;
import ar.edu.utn.frba.dds.models.repositories.canjeDePuntos.CanjeDePuntosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.OfertaDeProductosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.services.canjeDePuntos.CanjeDePuntosService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import java.time.LocalDateTime;

public class CanjeDePuntosController implements ICrudViewsHandler {

    private ColaboradorRepository colaboradorRepository;

    private CanjeDePuntosService canjeDePuntosService;
    private OfertaDeProductosRepository ofertaDeProductosRepository;



    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        context.render("canjeDePuntos/productos_canjear.hbs");
    }

    @Override
    public void save(Context context) {

        //TODO directamente hago get del optional porque es lit el usuario que inicio sesion...y el producto tambien, si esta en la vista ya existe bruh
        Colaborador colaboradorCanje = colaboradorRepository.buscarPorId(context.sessionAttribute("userId")).get();

        Double puntosCanjeados = Double.valueOf(context.formParam("puntos_canjeados"));

        //TODO creo que no llega como parametro sino que calcula con el futuro service
        Double puntosRestantes = Double.valueOf(context.formParam("puntos_restantes"));

        OfertaDeProductos oferta = ofertaDeProductosRepository.buscarPorId(context.formParam("oferta_id")).get();
        CanjeDePuntos canjeDePuntosNuevo = CanjeDePuntos.por(colaboradorCanje, LocalDateTime.now(), puntosCanjeados, puntosRestantes, oferta);

        this.canjeDePuntosService.guardar(canjeDePuntosNuevo);

        context.redirect("canje_de_puntos/canje_exitoso.hbs");

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

    public void obtenerPuntos(Context context){
        Colaborador colaborador = colaboradorRepository.buscarPorId(context.sessionAttribute("userId")).get();
        PuntosPorColaborador puntosPorColaborador = PuntosPorColaborador.of(colaborador, canjeDePuntosService);
        Double puntos = puntosPorColaborador.calcularPuntos();
        //TODO mandar los puntos a la vista

    }

}
