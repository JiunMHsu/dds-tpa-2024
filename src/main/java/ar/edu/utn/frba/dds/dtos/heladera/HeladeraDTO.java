package ar.edu.utn.frba.dds.dtos.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HeladeraDTO {
  private String id;

  private String nombre;

  private String estado;

  private String calle;

  private String altura;

  private String calleYAltura;

  private String barrio;

  private String capacidad;

  private String ubicacion;

  private String latitud;

  private String longitud;

  private String cantViandas; // cambie nombre, un poco mas expresivo

  private String fechaInicio;

  private String ultimaTemp;

  private String temperaturaMaxima;

  private String temperaturaMinima;

  private boolean estaActiva;

  public static HeladeraDTO completa(Heladera heladera) {

    String direccionString = heladera.getDireccion().getCalle().getNombre() + " " + heladera.getDireccion().getAltura().toString();
    String latitudLongitudString = String.valueOf(heladera.getDireccion().getUbicacion().getLatitud()) + ", " + String.valueOf(heladera.getDireccion().getUbicacion().getLongitud());

    String ultimaTempString = heladera.getUltimaTemperatura() != null
        ? heladera.getUltimaTemperatura().toString()
        : "--";

    return HeladeraDTO
        .builder()
        .id(heladera.getId().toString())
        .nombre(heladera.getNombre())
        .estado(heladera.getEstado().toString())
        .calleYAltura(direccionString)
        .barrio(heladera.getDireccion().getBarrio().getNombre())
        .capacidad(heladera.getCapacidad().toString())
        .ubicacion(latitudLongitudString)
        .cantViandas(heladera.getViandas().toString())
        .fechaInicio(DateTimeParser.parseFechaHora(heladera.getInicioFuncionamiento()))
        .ultimaTemp(ultimaTempString)
        .temperaturaMaxima(heladera.getRangoTemperatura().getMaxima().toString())
        .temperaturaMinima(heladera.getRangoTemperatura().getMinima().toString())
        .estaActiva(heladera.getEstado().equals(EstadoHeladera.ACTIVA))
        .build();
  }

  public static HeladeraDTO preview(Heladera heladera) {

    return HeladeraDTO
        .builder()
        .id(heladera.getId().toString())
        .nombre(heladera.getNombre())
        .estado(heladera.getEstado().toString())
        .calle(heladera.getDireccion().getCalle().getNombre())
        .altura(heladera.getDireccion().getAltura().toString())
        .latitud(String.valueOf(heladera.getDireccion().getUbicacion().getLatitud()))
        .longitud(String.valueOf(heladera.getDireccion().getUbicacion().getLongitud()))
        .cantViandas(heladera.getViandas().toString())
        .build();

  }
}
