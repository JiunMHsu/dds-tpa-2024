package ar.edu.utn.frba.dds.models.tecnico;

import ar.edu.utn.frba.dds.models.data.Area;
import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.data.Documento;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Tecnico {

  private String nombre;
  private String apellido;
  private Documento documento;
  private Long cuit;
  private Contacto contacto;
  private Area areaDeCobertura;

  public static Tecnico with(String nombre,
                             String apellido,
                             Documento documento,
                             Long cuit,
                             Contacto contacto,
                             Area areaDeCobertura) {
    return Tecnico
        .builder()
        .nombre(nombre)
        .apellido(apellido)
        .documento(documento)
        .cuit(cuit)
        .contacto(contacto)
        .areaDeCobertura(areaDeCobertura)
        .build();
  }

  public static Tecnico with(String nombre,
                             Contacto contacto,
                             Area areaDeCobertura) {
    return Tecnico
        .builder()
        .nombre(nombre)
        .contacto(contacto)
        .areaDeCobertura(areaDeCobertura)
        .build();
  }
}
