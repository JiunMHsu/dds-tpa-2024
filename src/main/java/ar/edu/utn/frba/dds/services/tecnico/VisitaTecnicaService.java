package ar.edu.utn.frba.dds.services.tecnico;

import ar.edu.utn.frba.dds.dtos.tecnico.CreateVisitaTecnicaDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.VisitaTecnicaDTO;
import ar.edu.utn.frba.dds.exceptions.IncicenteToFixException;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnica;
import ar.edu.utn.frba.dds.models.repositories.tecnico.VisitaTecnicaRepository;
import ar.edu.utn.frba.dds.services.images.ImageService;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.UploadedFile;
import java.io.IOException;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Servicio de visita técnica.
 */
public class VisitaTecnicaService implements WithSimplePersistenceUnit {

  private final VisitaTecnicaRepository visitaTecnicaRepository;
  private final IncidenteService incidenteService;
  private final ImageService imageService;

  /**
   * Constructor.
   *
   * @param visitaTecnicaRepository Repositorio de visita técnica
   * @param incidenteService        Servicio de incidente
   * @param imageService            Servicio de imagen
   */
  public VisitaTecnicaService(VisitaTecnicaRepository visitaTecnicaRepository,
                              IncidenteService incidenteService,
                              ImageService imageService) {
    this.visitaTecnicaRepository = visitaTecnicaRepository;
    this.incidenteService = incidenteService;
    this.imageService = imageService;
  }

  /**
   * Registra una visita técnica.
   *
   * @param tecnico     Técnico
   * @param nuevaVisita Visita técnica
   * @throws InvalidFormParamException Si la foto es nula
   * @throws IOException               Si ocurre un error al guardar la imagen
   * @throws IncicenteToFixException   Si el incidente ya está resuelto
   */
  public void registrarVisita(Tecnico tecnico, CreateVisitaTecnicaDTO nuevaVisita)
      throws InvalidFormParamException, IOException, IncicenteToFixException {
    Incidente incidente = this.incidenteService
        .buscarIncidentePorId(nuevaVisita.getIncidenteId());

    if (incidente.getEsResuelta()) {
      throw new IncicenteToFixException("El incidente ya está resuelto");
    }

    UploadedFile foto = nuevaVisita.getFoto();
    if (foto == null) {
      throw new InvalidFormParamException();
    }

    String pathFoto = imageService.guardarImagen(foto.content(), foto.extension());

    VisitaTecnica visitaTecnica = VisitaTecnica.por(
        tecnico,
        incidente,
        incidente.getHeladera(),
        nuevaVisita.getFechaHora(),
        nuevaVisita.getDescripcion(),
        nuevaVisita.isPudoResolverse(),
        new Imagen(pathFoto)
    );

    if (nuevaVisita.isPudoResolverse()) {
      this.incidenteService.resolverIncidente(incidente);
    }

    beginTransaction();
    this.visitaTecnicaRepository.guardar(visitaTecnica);
    commitTransaction();
  }

  /**
   * Busca todas las visitas técnicas.
   *
   * @return Lista de visitas técnicas
   */
  public List<VisitaTecnicaDTO> buscarTodas() {
    return this.visitaTecnicaRepository
        .buscarTodos()
        .stream().map(VisitaTecnicaDTO::fromVisitaTecnica).toList();
  }

  /**
   * Busca una visita técnica por su ID.
   *
   * @param id Id de la visita técnica
   * @return Visita técnica
   */
  public VisitaTecnica buscarPorId(@NotNull String id) {
    return this.visitaTecnicaRepository.buscarPorId(id)
        .orElseThrow(ResourceNotFoundException::new);
  }

  /**
   * Busca todas las visitas técnicas asociadas a un técnico.
   *
   * @param tecnico Técnico
   * @return Lista de visitas técnicas
   */
  public List<VisitaTecnicaDTO> buscarPorTecnico(Tecnico tecnico) {
    return this.visitaTecnicaRepository
        .buscarPorTecnico(tecnico)
        .stream().map(VisitaTecnicaDTO::fromVisitaTecnica).toList();
  }

  /**
   * Busca todas las visitas técnicas asociadas a un incidente.
   *
   * @param incidente Incidente
   * @return Lista de visitas técnicas
   */
  public List<VisitaTecnicaDTO> buscarPorincidente(Incidente incidente) {
    return this.visitaTecnicaRepository
        .buscarPorIncidente(incidente)
        .stream().map(VisitaTecnicaDTO::fromVisitaTecnica).toList();
  }
}
