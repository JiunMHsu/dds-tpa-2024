package ar.edu.utn.frba.dds.controllers.canjeDePuntos;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.ProductoDTO;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.services.canjeDePuntos.CanjeDePuntosService;
import ar.edu.utn.frba.dds.services.colaboraciones.OfertaProductosServiciosService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.UserRequired;
import io.javalin.http.Context;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CanjeDePuntosController extends UserRequired {
    private final CanjeDePuntosService canjeDePuntosService;
    private final OfertaProductosServiciosService ofertaProductosServiciosService;

    public CanjeDePuntosController(UsuarioService usuarioService, ColaboradorService colaboradorService, CanjeDePuntosService canjeDePuntosService, OfertaProductosServiciosService ofertaProductosServiciosService) {
        super(usuarioService, colaboradorService);
        this.canjeDePuntosService = canjeDePuntosService;
        this.ofertaProductosServiciosService = ofertaProductosServiciosService;
    }

    public void index(Context context) {
    }

    public void show(Context context) {
    }

    @Override
    public void create(Context context) {
        List<OfertaDeProductos> productos = this.ofertaProductosServiciosService.buscarTodos();

        List<ProductoDTO> productosDTOS = productos.stream()
                .map(ProductoDTO::preview)
                .collect(Collectors.toList());


        Colaborador colaborador = this.obtenerColaboradorPorSession(context);

        double puntaje = this.canjeDePuntosService.getPuntosDeColaborador(colaborador);

        Map<String, Object> model = new HashMap<>();
        model.put("productos-canjear", productosDTOS);
        model.put("titulo", "Listado de productos/servicios");
        model.put("puntaje", puntaje);

        context.render("canje_de_puntos/productos_canjear.hbs");
    }

    @Override
    public void save(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {
            //TODO - refactor extends colaboradorPorSession

            Colaborador colaboradorCanje = this.obtenerColaboradorPorSession(context);

            Double puntosCanjeados = Double.valueOf(context.formParam("puntos_canjeados"));

            //TODO creo que no llega como parametro sino que calcula con el futuro service
            Double puntosRestantes = Double.valueOf(context.formParam("puntos_restantes"));
            puntosRestantes = 3.14159;

            OfertaDeProductos oferta = this.ofertaProductosServiciosService.buscarPorId(context.formParam("oferta_id")).get();
            CanjeDePuntos canjeDePuntosNuevo = CanjeDePuntos.por(colaboradorCanje, oferta, LocalDateTime.now(), puntosCanjeados, 0); // no siempre es 0, hay q calcular los restantes

            this.canjeDePuntosService.registrar(canjeDePuntosNuevo);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/canjes-puntos", "Ver Productos"));
            /*no se si deberia ir una excepcion*/
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }
    }

}
