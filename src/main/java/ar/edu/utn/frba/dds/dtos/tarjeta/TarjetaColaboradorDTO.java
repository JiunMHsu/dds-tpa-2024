package ar.edu.utn.frba.dds.dtos.tarjeta;

import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TarjetaColaboradorDTO {
  private final String codigo;
  private final String duenio;
  private final Boolean estaActiva;

  /**
   * Genera el DTO de Tarjeta.
   *
   * @param tarjeta Tarjeta de Colaborador
   * @return TarjetaColaboradorDTO
   */
  public static TarjetaColaboradorDTO completa(TarjetaColaborador tarjeta) {

    String duenio = tarjeta.getDuenio().getNombre() + " " + tarjeta.getDuenio().getApellido();

    return TarjetaColaboradorDTO.builder()
        .codigo(tarjeta.getCodigo())
        .duenio(duenio)
        .estaActiva(tarjeta.getEstaActiva())
        .build();
  }
}
