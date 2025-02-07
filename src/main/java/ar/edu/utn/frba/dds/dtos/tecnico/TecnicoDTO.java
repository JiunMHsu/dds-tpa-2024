package ar.edu.utn.frba.dds.dtos.tecnico;

import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de Tecnico.
 */
@Getter
@Setter
@Builder
public class TecnicoDTO {
  private String id;
  private String nombre;
  private String apellido;
  private String documento;
  private String cuil;
  private String contacto;
  private String medioDeNotificacion;
  private String barrioArea;
  private String radioArea;
  private String latitudArea;
  private String longitudArea;

  /**
   * Genera el DTO completo de un Técnico.
   *
   * @param tecnico Técnico
   * @return TecnicoDTO
   */
  public static TecnicoDTO completa(Tecnico tecnico) {
    return TecnicoDTO
        .builder()
        .id(tecnico.getId().toString())
        .nombre(tecnico.getNombre())
        .apellido(tecnico.getApellido())
        .documento(tecnico.getDocumento().getNumero())
        .cuil(tecnico.getCuil())
        .medioDeNotificacion(tecnico.getContacto().getValor())
        .barrioArea(tecnico.getAreaDeCobertura().getBarrio().getNombre())
        .radioArea(tecnico.getAreaDeCobertura().getRadio().toString())
        .latitudArea(String.valueOf(tecnico.getAreaDeCobertura().getUbicacion().getLatitud()))
        .longitudArea(String.valueOf(tecnico.getAreaDeCobertura().getUbicacion().getLongitud()))
        .build();
  }

  /**
   * Genera el DTO preview de un Técnico.
   *
   * @param tecnico Técnico
   * @return TecnicoDTO
   */
  public static TecnicoDTO preview(Tecnico tecnico) {
    return TecnicoDTO
        .builder()
        .id(tecnico.getId().toString())
        .nombre(tecnico.getNombre())
        .apellido(tecnico.getApellido())
        .documento(tecnico.getDocumento().getNumero())
        .cuil(tecnico.getCuil())
        .barrioArea(tecnico.getAreaDeCobertura().getBarrio().getNombre())
        .build();
  }
}
