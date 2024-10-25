package ar.edu.utn.frba.dds.controllers.canjeDePuntos;

import ar.edu.utn.frba.dds.dtos.colaboraciones.OfertaDeProductosDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.puntosPorColaborador.CanjeDePuntos;
import ar.edu.utn.frba.dds.services.canjeDePuntos.CanjeDePuntosService;
import ar.edu.utn.frba.dds.services.colaboraciones.OfertaProductosServiciosService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CanjeDePuntosController implements ICrudViewsHandler {

    private ColaboradorService colaboradorService;
    private CanjeDePuntosService canjeDePuntosService;
    private OfertaProductosServiciosService ofertaProductosServiciosService;

    public CanjeDePuntosController(ColaboradorService colaboradorService, CanjeDePuntosService canjeDePuntosService, OfertaProductosServiciosService ofertaProductosServiciosService) {
        this.colaboradorService = colaboradorService;
        this.canjeDePuntosService = canjeDePuntosService;
        this.ofertaProductosServiciosService = ofertaProductosServiciosService;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        List<OfertaDeProductos> productos = this.ofertaProductosServiciosService.buscarTodos();

        List<OfertaDeProductosDTO> ofertaDeProductosDTOS = productos.stream()
                .map(OfertaDeProductosDTO::preview)
                .collect(Collectors.toList());

        String userId = context.sessionAttribute("userId");
        Optional<Colaborador> colaborador = colaboradorService.buscarPorId(userId);

        if (colaborador.isEmpty())
            throw new ResourceNotFoundException("No se encontró colaborador paraColaborador id " + userId);

        Double puntaje = canjeDePuntosService.calcularPuntos(colaborador.get());

        Map<String, Object> model = new HashMap<>();
        model.put("productos_canjear", ofertaDeProductosDTOS);
        model.put("titulo", "Listado por productos/servicios");
        model.put("puntaje", puntaje);

        context.render("canjeDePuntos/productos_canjear.hbs");
    }

    @Override
    public void save(Context context) {

        String userId = context.sessionAttribute("userId");
        Optional<Colaborador> colaboradorCanje = colaboradorService.buscarPorId(userId);

        if (colaboradorCanje.isEmpty())
            throw new ResourceNotFoundException("No se encontró colaborador paraColaborador id " + userId);

        Double puntosCanjeados = Double.valueOf(context.formParam("puntos_canjeados"));

        //TODO creo que no llega como parametro sino que calcula paraColaborador el futuro service
        Double puntosRestantes = Double.valueOf(context.formParam("puntos_restantes"));

        OfertaDeProductos oferta = ofertaProductosServiciosService.buscarPorId(context.formParam("oferta_id")).get();
        CanjeDePuntos canjeDePuntosNuevo = CanjeDePuntos.por(colaboradorCanje.get(), LocalDateTime.now(), puntosCanjeados, puntosRestantes, oferta);

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

}
