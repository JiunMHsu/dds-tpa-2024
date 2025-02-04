package ar.edu.utn.frba.dds.dtos.tecnico;

import io.javalin.http.UploadedFile;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object para Crear Visita Técnica.
 */
@Getter
@AllArgsConstructor
public class CreateVisitaTecnicaDTO {
  private final String incidenteId;
  private final LocalDateTime fechaHora;
  private final String descripcion;
  private final UploadedFile foto;
  private final boolean pudoResolverse;
}
