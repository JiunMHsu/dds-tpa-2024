package ar.edu.utn.frba.dds.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO para redirecciones.
 */
@Getter
@AllArgsConstructor
public class RedirectDTO {
  private final String url;
  private final String label;
}
