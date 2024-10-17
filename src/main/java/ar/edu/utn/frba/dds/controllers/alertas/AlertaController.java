package ar.edu.utn.frba.dds.controllers.alertas;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AlertaController implements ICrudViewsHandler {

  private IncidenteRepository incidenteRepository;

  private UsuarioRepository usuarioRepository;
  public AlertaController(IncidenteRepository incidenteRepository, UsuarioRepository usuarioRepository) {
    this.incidenteRepository = incidenteRepository;
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  public void index(Context context) {
    String idUsuario = context.sessionAttribute("idUsuario");

    if (idUsuario == null) {
      context.redirect("/login");
      return;
    }

    Optional<Usuario> usuarioOpt = this.usuarioRepository.buscarPorId(idUsuario);
    if (!usuarioOpt.isPresent()) {
      context.redirect("/login");
      return;
    }

    Usuario usuario = usuarioOpt.get();

    if (usuario.getRol() != TipoRol.COLABORADOR) {
      context.redirect("/prohibido");
      return;
    }

    List<Incidente> incidentes = incidenteRepository.obtenerSinFallasTecnicas();

    Map<String, Object> model = new HashMap<>();
    model.put("incidentes", incidentes);
    model.put("titulo", "Listado de alertas");

    context.render("alertas.hbs", model);
  }

  @Override
  public void show(Context context) {
    // si es necesario
  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void save(Context context) {

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