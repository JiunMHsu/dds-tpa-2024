package ar.edu.utn.frba.dds.controllers.suscripcion;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.heladera.HeladeraDTO;
import ar.edu.utn.frba.dds.dtos.suscripcion.CreateSuscripcionHeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.NotColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.SuscripcionFaltaViandaException;
import ar.edu.utn.frba.dds.exceptions.SuscripcionHeladeraLlenaException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
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


// TODO - documentar
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

  public void createSuscripcion(Context context, String pathSuscripcion) {

    String heladeraId = context.queryParamAsClass("heladera", String.class).get();

    Heladera heladera = this.heladeraService.buscarPorId(heladeraId);

    Map<String, Object> model = new HashMap<>();

    HeladeraDTO heladeraDto = HeladeraDTO.fromHeladra(heladera);
    model.put("heladera", heladeraDto);

    render(context, "suscripciones/" + pathSuscripcion, model);
  }

  public void create(Context context) {
    createSuscripcion(context, "suscripciones.hbs");
  }

  public void createFallaHeladera(Context context) {
    this.createSuscripcion(context, "suscripcion_falla_heladera.hbs");
  }

  public void createFaltaVianda(Context context) {
    this.createSuscripcion(context, "suscripcion_falta_viandas.hbs");
  }

  public void createHeladeraLlena(Context context) {
    this.createSuscripcion(context, "suscripcion_heladera_llena.hbs");
  }

  public void save(Context context) {

    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDtos = new ArrayList<>();
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
        nuevaSuscripcion.setViandasRestantes(context.formParamAsClass("cantidad-viandas",
            Integer.class).get());
        this.faltaViandaService.registrar(nuevaSuscripcion);
      } else if (tipoSuscripcion.contains("heladera-llena")) {
        nuevaSuscripcion.setEspacioRestante(context.formParamAsClass("viandas-restantes",
            Integer.class).get());
        this.heladeraLlenaService.registrar(nuevaSuscripcion);
      }

      operationSuccess = true;
      redirectDtos.add(new RedirectDTO("/heladeras", "Ir a Heladeras"));

    } catch (NotColaboratorException e) {
      throw new UnauthorizedException(e.getMessage());
    } catch (ValidationException | ResourceNotFoundException
             | SuscripcionFaltaViandaException | SuscripcionHeladeraLlenaException e) {
      redirectDtos.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDtos);
      context.render("post_result.hbs", model);
    }

  }

}
