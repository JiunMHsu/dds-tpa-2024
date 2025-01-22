package ar.edu.utn.frba.dds.controllers.canjeDePuntos;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.canjeDePuntos.CanjeDePuntosDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.ProductoDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.canjeDePuntos.CanjeDePuntosService;
import ar.edu.utn.frba.dds.services.colaboraciones.OfertaProductosServiciosService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanjeDePuntosController extends ColaboradorRequired {
  private final CanjeDePuntosService canjeDePuntosService;
  private final OfertaProductosServiciosService ofertaProductosServiciosService;

  public CanjeDePuntosController(UsuarioService usuarioService, ColaboradorService colaboradorService, CanjeDePuntosService canjeDePuntosService, OfertaProductosServiciosService ofertaProductosServiciosService) {
    super(usuarioService, colaboradorService);
    this.canjeDePuntosService = canjeDePuntosService;
    this.ofertaProductosServiciosService = ofertaProductosServiciosService;
  }

  public void index(Context context) {

    Colaborador colaborador = colaboradorFromSession(context);
    List<CanjeDePuntos> canjeDePuntos = this.canjeDePuntosService.buscarTodosxColaborador(colaborador);

    List<CanjeDePuntosDTO> canjeDePuntosDTOS = canjeDePuntos.stream()
        .map(CanjeDePuntosDTO::preview)
        .toList();

    Map<String, Object> model = new HashMap<>();
    model.put("canjes", canjeDePuntosDTOS);

    context.render("canje_de_puntos/historial_canjeos.hbs", model);
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
    model.put("productos", productosDTOS);
    model.put("puntaje", puntaje);

    render(context, "canje_de_puntos/canje_puntos_crear.hbs", model);
  }

  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      Colaborador colaborador = colaboradorFromSession(context);
      double puntaje = this.canjeDePuntosService.getPuntosDeColaborador(colaborador);

      String ofertaId = context.queryParam("oferta");
      OfertaDeProductos oferta = ofertaProductosServiciosService.buscarPorId(ofertaId)
          .orElseThrow(ResourceNotFoundException::new);


      double puntosRestantes = puntaje - oferta.getPuntosNecesarios();
      if (puntosRestantes < 0) {
        System.out.println("puntos insuficientes");
        throw new Exception();
      }

      CanjeDePuntos canjeDePuntosNuevo = CanjeDePuntos.por(colaborador, oferta, LocalDateTime.now(), oferta.getPuntosNecesarios(), puntosRestantes);
      this.canjeDePuntosService.registrar(canjeDePuntosNuevo);

      operationSuccess = true;
      redirectDTOS.add(new RedirectDTO("/canje-de-puntos/new", "Ver Productos"));
    } catch (Exception e) {
      redirectDTOS.add(new RedirectDTO("/canje-de-puntos/new", "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
      context.render("post_result.hbs", model);
    }
  }

}
