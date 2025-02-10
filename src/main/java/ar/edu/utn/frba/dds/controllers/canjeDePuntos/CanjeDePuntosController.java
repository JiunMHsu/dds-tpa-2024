package ar.edu.utn.frba.dds.controllers.canjeDePuntos;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.canjeDePuntos.CanjeDePuntosDTO;
import ar.edu.utn.frba.dds.dtos.canjeDePuntos.ProductoDTO;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.canjeDePuntos.CanjeDePuntosService;
import ar.edu.utn.frba.dds.services.colaboraciones.OfertaProductosServiciosService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller de Canje de Puntos.
 */
public class CanjeDePuntosController extends ColaboradorRequired {

  private final CanjeDePuntosService canjeDePuntosService;
  private final OfertaProductosServiciosService ofertaProductosServiciosService;

  /**
   * Constructor de CanjeDePuntosController.
   *
   * @param usuarioService                  Servicio de Usuario
   * @param colaboradorService              Servicio de Colaborador
   * @param canjeDePuntosService            Servicio de Canje de Puntos
   * @param ofertaProductosServiciosService Servicio de Oferta Productos o Servicios
   */
  public CanjeDePuntosController(UsuarioService usuarioService,
                                 ColaboradorService colaboradorService,
                                 CanjeDePuntosService canjeDePuntosService,
                                 OfertaProductosServiciosService ofertaProductosServiciosService) {
    super(usuarioService, colaboradorService);
    this.canjeDePuntosService = canjeDePuntosService;
    this.ofertaProductosServiciosService = ofertaProductosServiciosService;
  }

  /**
   * Devuelve la vista de todos los canjes de puntos.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void index(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);
    List<CanjeDePuntosDTO> canjeDePuntosDtos = this.canjeDePuntosService
        .buscarPorColaborador(colaborador);

    Map<String, Object> model = new HashMap<>();
    model.put("canjes", canjeDePuntosDtos);

    render(context, "canje_de_puntos/historial_canjeos.hbs", model);
  }

  /**
   * Devuelve una lista de productos para canjear.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void create(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);
    double puntaje = this.canjeDePuntosService.getPuntosDeColaborador(colaborador);

    List<ProductoDTO> productos = this.ofertaProductosServiciosService.buscarTodosProductos();

    Map<String, Object> model = new HashMap<>();
    model.put("productos", productos);
    model.put("puntaje", puntaje);

    render(context, "canje_de_puntos/canje_puntos_crear.hbs", model);
  }

  /**
   * Registra un canje de puntos.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      this.canjeDePuntosService.registrar(
          colaboradorFromSession(context),
          context.queryParam("oferta")
      );

      operationSuccess = true;
      redirects.add(new RedirectDTO("/canje-de-puntos/new", "Ver Productos"));
    } catch (Exception e) {
      redirects.add(new RedirectDTO("/canje-de-puntos/new", "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);
      render(context, "post_result.hbs", model);
    }
  }
}
