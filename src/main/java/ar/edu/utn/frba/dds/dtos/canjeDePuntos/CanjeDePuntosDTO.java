package ar.edu.utn.frba.dds.dtos.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.CanjeDePuntos;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Canje de Puntos DTO.
 */
@Getter
@Setter
@Builder
public class CanjeDePuntosDTO {

  private String id;

  private String colaboradorCanjeador;

  private String nombreProducto;

  private String rubroProducto;

  private String imagenProducto;

  private String colaboradorPublicador;

  private String fechaHoraCanjeo;

  private String puntosCanjeados;

  private String puntosRestantes;

  /**
   * Builder del DTO CanjeDePuntos.
   *
   * @param canjeDePuntos           Canje de puntos
   * @return DTO CanjeDePuntos
   */
  public static CanjeDePuntosDTO preview(CanjeDePuntos canjeDePuntos) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String fechaHoraCanjeo = canjeDePuntos.getFechaHora().format(formatter);

    return CanjeDePuntosDTO
        .builder()
        .id(canjeDePuntos.getId().toString())
        .nombreProducto(canjeDePuntos.getOferta().getNombre())
        .rubroProducto(canjeDePuntos.getOferta().getRubro().name())
        .imagenProducto(canjeDePuntos.getOferta().getImagen().getRuta())
        .colaboradorPublicador(canjeDePuntos.getOferta().getColaborador().getNombre())
        .fechaHoraCanjeo(fechaHoraCanjeo)
        .puntosCanjeados(String.valueOf(canjeDePuntos.getPuntosCanjeados()))
        .build();
  }
}
