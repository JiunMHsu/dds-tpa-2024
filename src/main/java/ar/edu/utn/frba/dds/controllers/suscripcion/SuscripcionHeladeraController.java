package ar.edu.utn.frba.dds.controllers.suscripcion;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.heladera.HeladeraDTO;
import ar.edu.utn.frba.dds.dtos.suscripcion.CreateSuscripcionHeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.NotColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.SuscripcionFaltaViandaException;
import ar.edu.utn.frba.dds.exceptions.SuscripcionHeladeraLlenaException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.suscripcion.FallaHeladeraService;
import ar.edu.utn.frba.dds.services.suscripcion.FaltaViandaService;
import ar.edu.utn.frba.dds.services.suscripcion.HeladeraLlenaService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// TODO - Refactorizar
public class SuscripcionHeladeraController extends ColaboradorRequired {

  private final HeladeraService heladeraService;
  private final FallaHeladeraService fallaHeladeraService;
  private final FaltaViandaService faltaViandaService;
  private final HeladeraLlenaService heladeraLlenaService;

  public SuscripcionHeladeraController(HeladeraService heladeraService,
                                       FallaHeladeraService fallaHeladeraService,
                                       FaltaViandaService faltaViandaService,
                                       HeladeraLlenaService heladeraLlenaService,
                                       UsuarioService usuarioService,
                                       ColaboradorService colaboradorService) {
    super(usuarioService, colaboradorService);
    this.heladeraService = heladeraService;
    this.fallaHeladeraService = fallaHeladeraService;
    this.faltaViandaService = faltaViandaService;
    this.heladeraLlenaService = heladeraLlenaService;
  }

  public void create(Context context) {

    String heladeraId = context.queryParamAsClass("heladera", String.class).get();
//TODO ver donde poner la logica del DTO
    Heladera heladera = this.heladeraService.buscarPorId(heladeraId);

    Map<String, Object> model = new HashMap<>();

    HeladeraDTO heladeraDTO = HeladeraDTO.fromHeladra(heladera);
    model.put("heladera", heladeraDTO);

    render(context, "suscripciones/suscripciones.hbs", model);
  }

  public void createFallaHeladera(Context context) {

    String heladeraId = context.queryParamAsClass("heladera", String.class).get();

    HeladeraDTO heladeraDTO = this.heladeraService.buscarPorIdDto(heladeraId);

    Map<String, Object> model = new HashMap<>();

    model.put("heladera", heladeraDTO);

    render(context, "suscripciones/suscripcion_falla_heladera.hbs", model);
  }

  public void createFaltaVianda(Context context) {

    String heladeraId = context.queryParamAsClass("heladera", String.class).get();

    HeladeraDTO heladeraDTO = this.heladeraService.buscarPorIdDto(heladeraId);

    Map<String, Object> model = new HashMap<>();

    model.put("heladera", heladeraDTO);

    render(context, "suscripciones/suscripcion_falta_viandas.hbs", model);
  }

  public void createHeladeraLlena(Context context) {

    String heladeraId = context.queryParamAsClass("heladera", String.class).get();

    HeladeraDTO heladeraDTO = this.heladeraService.buscarPorIdDto(heladeraId);

    Map<String, Object> model = new HashMap<>();

    model.put("heladera", heladeraDTO);

    render(context, "suscripciones/suscripcion_heladera_llena.hbs", model);
  }

  public void save(Context context) {

    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      CreateSuscripcionHeladeraDTO nuevaSuscripcion = new CreateSuscripcionHeladeraDTO(
          colaboradorFromSession(context),
          this.heladeraService.buscarPorId(context.queryParamAsClass("heladera",
              String.class).get()),
          MedioDeNotificacion.valueOf(context.formParamAsClass("medio-notificacion",
              String.class).get()),
          context.formParamAsClass("contacto", String.class).get()
      );

      String tipoSuscripcion = context.path();

      if (tipoSuscripcion.contains("falla-tecnica")) {
        this.fallaHeladeraService.registrar(nuevaSuscripcion);
      } else if (tipoSuscripcion.contains("falta-viandas")) {
        nuevaSuscripcion.setViandasRestantes(context.formParamAsClass("cantidad-viandas", Integer.class).get());
        this.faltaViandaService.registrar(nuevaSuscripcion);
      } else if (tipoSuscripcion.contains("heladera-llena")) {
        nuevaSuscripcion.setEspacioRestante(context.formParamAsClass("viandas-restantes", Integer.class).get());
        this.heladeraLlenaService.registrar(nuevaSuscripcion);
      }

      operationSuccess = true;
      redirectDTOS.add(new RedirectDTO("/heladeras", "Ir a Heladeras"));

    } catch (NotColaboratorException e) {
      throw new UnauthorizedException(e.getMessage());
    } catch (ValidationException | ResourceNotFoundException
             | SuscripcionFaltaViandaException | SuscripcionHeladeraLlenaException e) {
      redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
      context.render("post_result.hbs", model);
    }

  }

}
