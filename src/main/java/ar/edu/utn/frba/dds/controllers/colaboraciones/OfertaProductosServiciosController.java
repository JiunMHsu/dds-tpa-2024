package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.colaboraciones.DonacionDineroDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.OfertaDeProductosDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RubroOferta;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.OfertaDeProductosRepository;
import ar.edu.utn.frba.dds.services.colaboraciones.OfertaProductosServiciosService;
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

    private OfertaProductosServiciosService ofertaProductosServiciosService;


    public OfertaProductosServiciosController(OfertaProductosServiciosService ofertaProductosServiciosService,
                                              UsuarioService usuarioService,
                                              ColaboradorService colaboradorService) {

        super(usuarioService, colaboradorService);
        this.ofertaProductosServiciosService = ofertaProductosServiciosService;
    }

    @Override
    public void index(Context context) {
        List<OfertaDeProductos> productos = this.ofertaProductosServiciosService.buscarTodos();

        List<OfertaDeProductosDTO> ofertaDeProductosDTOS = productos.stream()
                .map(OfertaDeProductosDTO::preview)
                .collect(Collectors.toList());

        Map<String, Object> model = new HashMap<>();
        model.put("colaboraciones", ofertaDeProductosDTOS);
        model.put("titulo", "Listado de productos/servicios");

        context.render("colaboraciones/colaboraciones.hbs", model);
    }

    @Override
    public void show(Context context) {
        String ofertaProductoId = context.pathParam("id");
        Optional<OfertaDeProductos> ofertaDeProductos = this.ofertaProductosServiciosService.buscarPorId(ofertaProductoId);

        if (ofertaDeProductos.isEmpty())
            throw new ResourceNotFoundException("No se encontró ninguna oferta de producto/servicio con id " + ofertaProductoId);


        Map<String, Object> model = new HashMap<>();
        OfertaDeProductosDTO ofertaDeProductosDTO = OfertaDeProductosDTO.completa(ofertaDeProductos.get());

        model.put("oferta_producto_servicio", ofertaDeProductosDTO);

        context.render("canje_de_puntos/producto_detalle.hbs", model);

    }

    @Override
    public void create(Context context) {
        context.render("colaboraciones/oferta_prod_serv_crear.hbs");

    }

    @Override
    public void save(Context context) {
        Colaborador colaborador = obtenerColaboradorPorSession(context);

        String nombre = context.formParam("nombre");
        Double puntosNecesarios = Double.valueOf(context.formParam("puntos_necesarios"));
        RubroOferta rubro = RubroOferta.valueOf(context.formParam("rubro"));
        Imagen imagen = new Imagen(context.formParam("imagen"));

        OfertaDeProductos oferta = OfertaDeProductos.por(colaborador, LocalDateTime.now(), nombre, puntosNecesarios, rubro, imagen);

        this.ofertaProductosServiciosService.guardar(oferta);
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
        String ofertaProductoId = context.pathParam("id");

        Optional<OfertaDeProductos> posibleOfertaAEliminar = this.ofertaProductosServiciosService.buscarPorId(ofertaProductoId);

        if (posibleOfertaAEliminar.isEmpty())
            throw new ResourceNotFoundException("No se encontró ninguna oferta de producto/servicio con id " + ofertaProductoId);

        Colaborador colaboradorOfertante = posibleOfertaAEliminar.get().getColaborador();

        Colaborador colaboradorSession = obtenerColaboradorPorSession(context);

        if(colaboradorOfertante!=colaboradorSession){
            throw new UnauthorizedException("No tiene permiso para eliminar la oferta");
        }

        this.ofertaProductosServiciosService.eliminar(posibleOfertaAEliminar.get());
        context.status(HttpStatus.OK);
        // TODO mostrar algo de exitoso?

    }

}
