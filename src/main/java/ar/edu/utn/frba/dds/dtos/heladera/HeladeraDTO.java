package ar.edu.utn.frba.dds.dtos.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO de heladera.
 */
@Getter
@Builder
public class HeladeraDTO {

  private String id;
  private String nombre;
  private String estado;
  private String direccion;
  private String capacidad;
  private String ubicacion;
  private String latitud;
  private String longitud;
  private String cantViandas;
  private String fechaInicio;
  private String ultimaTemp;
  private String temperaturaMaxima;
  private String temperaturaMinima;
  private boolean estaActiva;

  /**
   * Convierte una heladera en un DTO.
   *
   * @param heladera Heladera
   * @return HeladeraDTO
   */
  public static HeladeraDTO fromHeladra(Heladera heladera) {
    String ultimaTempString = heladera.getUltimaTemperatura() != null
        ? heladera.getUltimaTemperatura().toString()
        : "--";

    return HeladeraDTO
        .builder()
        .id(heladera.getId().toString())
        .nombre(heladera.getNombre())
        .estado(heladera.getEstado().toString())
        .direccion(heladera.getDireccion().toString())
        .capacidad(heladera.getCapacidad().toString())
        .ubicacion(heladera.getDireccion().getUbicacion().toString())
        .cantViandas(heladera.getViandas().toString())
        .fechaInicio(DateTimeParser.parseFechaHora(heladera.getInicioFuncionamiento()))
        .ultimaTemp(ultimaTempString)
        .temperaturaMaxima(heladera.getRangoTemperatura().getMaxima().toString())
        .temperaturaMinima(heladera.getRangoTemperatura().getMinima().toString())
        .estaActiva(heladera.estaActiva())
        .build();
  }
}
