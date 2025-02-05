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

  private final String id;
  private final String nombre;
  private final String estado; // ACTIVA, INACTIVA
  private final String direccion; // calle, altura, barrio
  private final String capacidad;
  private final String ubicacion; // latitud y longitud
  private final String cantViandas;
  private final String fechaInicio;
  private final String ultimaTemp;
  private final String temperaturaMaxima;
  private final String temperaturaMinima;
  private final boolean estaActiva;

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
