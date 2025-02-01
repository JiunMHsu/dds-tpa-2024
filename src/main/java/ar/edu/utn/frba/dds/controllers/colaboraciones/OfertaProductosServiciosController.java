package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.ofertaDeProductos.CreateOfertaDeProductosDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.ofertaDeProductos.OfertaDeProductosDTO;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RubroOferta;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaboraciones.OfertaProductosServiciosService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de Oferta de Productos y Servicios.
 */
public class OfertaProductosServiciosController extends ColaboradorRequired {

  private final OfertaProductosServiciosService ofertaProductosServiciosService;

  /**
   * Constructor.
   *
   * @param usuarioService     UsuarioService
   * @param colaboradorService ColaboradorService
   * @param ofertasService     OfertaProductosServiciosService
   */
  public OfertaProductosServiciosController(UsuarioService usuarioService,
                                            ColaboradorService colaboradorService,
                                            OfertaProductosServiciosService ofertasService) {

    super(usuarioService, colaboradorService);
    this.ofertaProductosServiciosService = ofertasService;
  }

  /**
   * Muestra el detalle de una oferta de producto/servicio.
   * TODO: Revisar
   *
   * @param context Context de Javalin.
   */
  public void show(Context context) {
    String ofertaProductoId = context.pathParam("id");

    OfertaDeProductosDTO ofertaDeProducto = OfertaDeProductosDTO.fromColaboracion(
        this.ofertaProductosServiciosService.buscarPorId(ofertaProductoId)
    );

    Map<String, Object> model = new HashMap<>();
    model.put("oferta_producto_servicio", ofertaDeProducto);
    render(context, "colaboraciones/oferta_prod_serv/oferta_prod_serv_detalle.hbs", model);
  }

  /**
   * Muestra el formulario para crear una oferta de producto/servicio.
   *
   * @param context Context de Javalin.
   */
  public void create(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);
    if (!colaborador.puedeColaborar(TipoColaboracion.OFERTA_DE_PRODUCTOS)) {
      throw new UnauthorizedException("No tenes permiso");
    }

    List<String> rubros = Arrays.stream(RubroOferta.values())
        .map(Enum::toString).toList();

    Map<String, Object> model = new HashMap<>();
    model.put("rubros", rubros);
    render(context, "colaboraciones/oferta_prod_serv/oferta_prod_serv_crear.hbs", model);
  }

  /**
   * Guarda una oferta de producto/servicio.
   *
   * @param context Context de Javalin.
   */
  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    Colaborador colaborador = colaboradorFromSession(context);
    if (!colaborador.puedeColaborar(TipoColaboracion.OFERTA_DE_PRODUCTOS)) {
      throw new UnauthorizedException("No tenes permiso");
    }

    try {

      CreateOfertaDeProductosDTO oferta = new CreateOfertaDeProductosDTO(
          context.formParamAsClass("nombre", String.class).get(),
          context.formParamAsClass("puntos", Double.class).get(),
          context.formParamAsClass("rubro", String.class).get(),
          context.uploadedFile("imagen")
      );

      this.ofertaProductosServiciosService.registrarOferta(colaborador, oferta);

      operationSuccess = true;
      redirects.add(new RedirectDTO("/colaboraciones", "Seguir Colaborando"));

    } catch (ValidationException | InvalidFormParamException | IOException e) {
      redirects.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);
      render(context, "post_result.hbs", model);
    }
  }

}
