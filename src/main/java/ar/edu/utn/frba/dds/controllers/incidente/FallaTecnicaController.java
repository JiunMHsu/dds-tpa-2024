package ar.edu.utn.frba.dds.controllers.incidente;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.heladera.HeladeraDTO;
import ar.edu.utn.frba.dds.dtos.incidente.FallaTecnicaDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.VisitaTecnicaDTO;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.images.ImageService;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.tecnico.VisitaTecnicaService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.validation.ValidationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FallaTecnicaController extends ColaboradorRequired {

  private final IncidenteService incidenteService;
  private final HeladeraService heladeraService;
  private final VisitaTecnicaService visitaTecnicaService;
  private final ImageService fileService;

  public FallaTecnicaController(UsuarioService usuarioService,
                                ColaboradorService colaboradorService,
                                IncidenteService incidenteService,
                                HeladeraService heladeraService,
                                VisitaTecnicaService visitaTecnicaService,
                                ImageService fileService) {
    super(usuarioService, colaboradorService);
    this.incidenteService = incidenteService;
    this.heladeraService = heladeraService;
    this.visitaTecnicaService = visitaTecnicaService;
    this.fileService = fileService;
  }

  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();

    String filtro = context.queryParamAsClass("filtro", String.class).getOrDefault("todas");

    List<Incidente> fallasTecnicas = this.incidenteService.buscarTodasFallasTecnicas();
    List<FallaTecnicaDTO> fallasTecnicasDTO = switch (filtro) {
      case "resueltas" -> fallasTecnicas.stream()
          .filter(Incidente::getEsResuelta)
          .map(FallaTecnicaDTO::preview).toList();
      case "pendientes" -> fallasTecnicas.stream()
          .filter(falla -> !falla.getEsResuelta())
          .map(FallaTecnicaDTO::preview).toList();
      default -> fallasTecnicas.stream()
          .map(FallaTecnicaDTO::preview).toList();
    };

    model.put("fallas", fallasTecnicasDTO);
    render(context, "incidentes/fallas_tecnicas.hbs", model);
  }

  public void show(Context context) {
    String fallaId = context.pathParam("id");

    Incidente falla = this.incidenteService.buscarIncidentePorId(fallaId)
        .orElseThrow(ResourceNotFoundException::new);

    Heladera heladera = falla.getHeladera();

    Usuario usuario = usuarioFromSession(context);
    boolean puedeResolver = usuario.getRol().isTecnico() && !falla.getEsResuelta();

    List<VisitaTecnicaDTO> visitasPreviasDTO = this.visitaTecnicaService
        .buscarPorincidente(falla)
        .stream().map(VisitaTecnicaDTO::preview)
        .toList();

    Map<String, Object> model = new HashMap<>();

    model.put("heladera", HeladeraDTO.preview(heladera));
    model.put("falla", FallaTecnicaDTO.completa(falla));
    model.put("puedeResolver", puedeResolver);

    model.put("visitasPrevias", visitasPreviasDTO);

    render(context, "incidentes/falla_tecnica_detalle.hbs", model);
  }

  public void create(Context context) {
    render(context, "incidentes/falla_tecnica_crear.hbs", new HashMap<>());
  }

  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      Colaborador colaborador = colaboradorFromSession(context);

      String nombreHeladera = context.formParamAsClass("nombre", String.class).get();
      Heladera heladera = this.heladeraService
          .buscarPorNombre(nombreHeladera)
          .orElseThrow(InvalidFormParamException::new);

      String descripcion = context.formParamAsClass("descripcion", String.class).get();

      UploadedFile uploadedFile = context.uploadedFile("imagen");
      if (uploadedFile == null) throw new InvalidFormParamException();
      String pathImagen = fileService.guardarImagen(uploadedFile.content(), uploadedFile.extension());

      Incidente nuevaFallaTecnica = Incidente.fallaTecnica(
          heladera,
          LocalDateTime.now(),
          colaborador,
          descripcion,
          new Imagen(pathImagen)
      );

      this.incidenteService.registrarIncidente(nuevaFallaTecnica);

      operationSuccess = true;
      redirectDTOS.add(new RedirectDTO("/fallas-tecnicas/new", "Reportar otra Falla"));

    } catch (ValidationException | InvalidFormParamException | IOException e) {
      redirectDTOS.add(new RedirectDTO("/fallas-tecnicas/new", "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);

      context.render("post_result.hbs", model);
    }
  }

}
