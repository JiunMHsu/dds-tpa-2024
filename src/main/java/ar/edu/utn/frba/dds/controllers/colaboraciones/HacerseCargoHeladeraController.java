package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.HacerseCargoHeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.NonColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaboraciones.HacerseCargoHeladeraService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HacerseCargoHeladeraController extends ColaboradorRequired implements ICrudViewsHandler {

  private final HacerseCargoHeladeraService hacerseCargoHeladeraService;
  private final HeladeraService heladeraService;


  public HacerseCargoHeladeraController(UsuarioService usuarioService,
                                        ColaboradorService colaboradorService,
                                        HacerseCargoHeladeraService hacerseCargoHeladeraService,
                                        HeladeraService heladeraService) {

    super(usuarioService, colaboradorService);
    this.heladeraService = heladeraService;
    this.hacerseCargoHeladeraService = hacerseCargoHeladeraService;
  }

  @Override
  public void index(Context context) {
    // TODO - Implementar
  }

  @Override
  public void show(Context context) { // TODO - Revisar
    String hacerseCargoHeladeraId = context.pathParam("id");
    Optional<HacerseCargoHeladera> hacerseCargoHeladera = hacerseCargoHeladeraService.buscarPorId(hacerseCargoHeladeraId);

    if (hacerseCargoHeladera.isEmpty())
      throw new ResourceNotFoundException("No se encontró un encargo de heladera para el colaborador con id " + hacerseCargoHeladeraId);

    Map<String, Object> model = new HashMap<>();

    HacerseCargoHeladeraDTO hacerseCargoHeladeraDTO = HacerseCargoHeladeraDTO.fromColaboracion(hacerseCargoHeladera.get());
    model.put("hacerse_cargo_heladera", hacerseCargoHeladeraDTO);

    context.render("colaboraciones/colaboracion_detalle.hbs", model);
  }

  @Override
  public void create(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);

    System.out.println(colaborador.getFormasDeColaborar());

    if (!colaborador.puedeColaborar(TipoColaboracion.HACERSE_CARGO_HELADERA))
      throw new UnauthorizedException("No tienes permiso");

    render(context, "colaboraciones/encargarse_de_heladera_crear.hbs", new HashMap<>());
  }

  @Override
  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      Colaborador colaborador = colaboradorFromSession(context);

      Heladera heladeraAEncargarse = heladeraService
          .buscarPorNombre(context.formParamAsClass("heladera", String.class).get())
          .orElseThrow(ResourceNotFoundException::new);

      HacerseCargoHeladera hacerseCargoHeladera = HacerseCargoHeladera.por(colaborador, LocalDateTime.now(), heladeraAEncargarse);

      this.hacerseCargoHeladeraService.registrar(hacerseCargoHeladera);

      // TODO - Delegar a Service??
      colaborador.invalidarPuntos();
      this.colaboradorService.actualizar(colaborador);

      operationSuccess = true;
      redirectDTOS.add(new RedirectDTO("/colaboraciones", "Seguir Colaborando"));

    } catch (NonColaboratorException e) {
      throw new UnauthorizedException();
    } catch (ValidationException | ResourceNotFoundException e) {
      redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
      context.render("post_result.hbs", model);
    }
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

