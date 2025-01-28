package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.ColaboracionDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.TipoColaboracionDTO;
import ar.edu.utn.frba.dds.exceptions.CargaMasivaException;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaboraciones.ColaboracionService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Controller de colaboraciones.
 */
public class ColaboracionController extends ColaboradorRequired {

  private final ColaboracionService colaboracionService;

  /**
   * Constructor de ColaboracionController.
   *
   * @param usuarioService      Servicio de Usuario
   * @param colaboradorService  Servicio de Colaborador
   * @param colaboracionService Servicio de Colaboracion
   */
  public ColaboracionController(UsuarioService usuarioService,
                                ColaboradorService colaboradorService,
                                ColaboracionService colaboracionService) {
    super(usuarioService, colaboradorService);
    this.colaboracionService = colaboracionService;
  }

  /**
   * Devuelve la vista de todas las colaboraciones.
   * Si el usuario es un colaborador, se le muestran únicamente las colaboraciones que realizó.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();
    Usuario usuario = usuarioFromSession(context);
    List<ColaboracionDTO> colaboracionesRealizadas = new ArrayList<>();

    if (Objects.equals(usuario.getRol(), TipoRol.COLABORADOR)) {
      Colaborador colaborador = colaboradorFromSession(context);
      colaboracionesRealizadas = colaboracionService.buscarTodasPorColaborador(colaborador);

      List<TipoColaboracionDTO> formasDeColaborar = colaborador.getFormasDeColaborar()
          .stream().map(TipoColaboracionDTO::redirectable)
          .toList();

      model.put("formasDeColaborar", formasDeColaborar);
      model.put("colaboradorId", colaborador.getId().toString());
    } else if (Objects.equals(usuario.getRol(), TipoRol.ADMIN)) {
      colaboracionesRealizadas = this.colaboracionService.buscarTodas();
    }

    model.put("colaboracionesRealizadas", colaboracionesRealizadas);
    render(context, "colaboraciones/colaboraciones.hbs", model);
  }

  /**
   * Handler para cargar colaboraciones desde un archivo CSV.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void cargarColaboraciones(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      UploadedFile uploadedFile = context.uploadedFile("csv");
      if (uploadedFile == null) {
        throw new InvalidFormParamException();
      }

      colaboracionService.cargarColaboraciones(uploadedFile.content());

      operationSuccess = true;
      redirects.add(new RedirectDTO("/colaboraciones", "Ver Colaboraciones"));

    } catch (ValidationException | CargaMasivaException e) {
      System.out.println(e.getMessage());
      redirects.add(new RedirectDTO("/colaboraciones", "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);

      render(context, "post_result.hbs", model);
    }
  }
}
