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

    public void create(Context context) {
        Colaborador colaborador = colaboradorFromSession(context);
        double puntaje = this.canjeDePuntosService.getPuntosDeColaborador(colaborador);

        List<OfertaDeProductos> productos = this.ofertaProductosServiciosService.buscarTodos();
        List<ProductoDTO> productosDTOS = productos.stream()
                .map(ProductoDTO::preview)
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("productos-canjear", productosDTOS);
        model.put("puntaje", puntaje);

        render(context, "canje_de_puntos/productos_canjear.hbs", model);
    }

    public void save(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {
            Colaborador colaboradorCanje = colaboradorFromSession(context);


            // TODO ===================================


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
