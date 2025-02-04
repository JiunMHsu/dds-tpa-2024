package ar.edu.utn.frba.dds.dtos.incidente;

import io.javalin.http.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de Crear Falla Técnica.
 */
@Getter
@AllArgsConstructor
public class CreateFallaTecnicaDTO {
  private final String heladera;
  private final String descripcion;
  private final UploadedFile foto;
}
