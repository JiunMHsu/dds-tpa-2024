package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.colaboraciones.OfertaDeProductosDTO;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RubroOferta;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.OfertaDeProductosRepository;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ColaboradorPorSession;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OfertaProductosServiciosController extends ColaboradorPorSession implements ICrudViewsHandler {

    private OfertaDeProductosRepository ofertaDeProductosRepository;

    public OfertaProductosServiciosController(OfertaDeProductosRepository ofertaDeProductosRepository,
                                              UsuarioService usuarioService,
                                              ColaboradorService colaboradorService) {

        super(usuarioService, colaboradorService);
        this.ofertaDeProductosRepository = ofertaDeProductosRepository;
    }

    @Override
    public void index(Context context) {
        List<OfertaDeProductos> productos = this.ofertaDeProductosRepository.buscarTodos();

        List<OfertaDeProductosDTO> ofertaDeProductosDTOS = productos.stream()
                .map(OfertaDeProductosDTO::preview)
                .collect(Collectors.toList());

        Map<String, Object> model = new HashMap<>();
        model.put("productos_canjear", ofertaDeProductosDTOS);
        model.put("titulo", "Listado de productos/servicios");

        context.render("canje_de_puntos/productos_canjear.hbs", model);
    }

    @Override
    public void show(Context context) {
        Optional<OfertaDeProductos> posibleOfertaBuscada = this.ofertaDeProductosRepository.buscarPorId(context.formParam("id"));
        //TODO verificar empty

        Map<String, Object> model = new HashMap<>();
        model.put("producto/servicio", posibleOfertaBuscada.get());

        context.render("canje_de_puntos/producto_detalle.hbs", model);

    }

    @Override
    public void create(Context context) {
        context.render("colaboraciones/oferta_prod_serv_crear.hbs");

    }

    @Override
    public void save(Context context) {
        Colaborador colaborador = this.obtenerColaboradorPorSession(context.sessionAttribute("userId"));
        String nombre = context.formParam("nombre");
        Double puntosNecesarios = Double.valueOf(context.formParam("puntos_necesarios"));
        RubroOferta rubro = RubroOferta.valueOf(context.formParam("rubro"));
        Imagen imagen = new Imagen(context.formParam("imagen"));

        OfertaDeProductos oferta = OfertaDeProductos.por(colaborador, LocalDateTime.now(), nombre, puntosNecesarios, rubro, imagen);

        this.ofertaDeProductosRepository.guardar(oferta);
        context.redirect("post_result.hbs");

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {
        //TODO chequeo que usuario sea el de la oferta
        Optional<OfertaDeProductos> posibleOfertaAEliminar = this.ofertaDeProductosRepository.buscarPorId(context.formParam("id"));
        // TODO - chequeo si no existe

        this.ofertaDeProductosRepository.eliminar(posibleOfertaAEliminar.get());
        context.status(HttpStatus.OK);
        // mostrar algo de exitoso

    }

}
