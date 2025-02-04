package ar.edu.utn.frba.dds.dtos.colaborador;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Create Colaborador DTO.
 */
@Getter
@AllArgsConstructor
@Builder
public class CreateColaboradorDTO {
  private final String tipo;
  private final String nombre;
  private final String apellido;
  private final String fechaNacimiento;
  private final String razonSocial;
  private final String tipoRazonSocial;
  private final String rubro;
  private final String barrio;
  private final String calle;
  private final String altura;
  private final String telefono;
  private String formaDeColaborar;

  /**
   * Builder del DTO Colaborador con los atributos de un Colaborador Humano.
   *
   * @param nombre           Nombre del Colaborador
   * @param apellido         Apellido del Colaborador
   * @param fechaNacimiento  Fecha de Nacimiento del Colaborador
   * @param barrio           Barrio de la Dirección del Colaborador
   * @param calle            Calle de la Dirección del Colaborador
   * @param altura           Altura de la Dirección del Colaborador
   * @param telefono         Telefono del Colaborador
   * @param formaDeColaborar Formas de Colaborar elegidas por el Colaborador
   * @return DTO Create Colaborador en formato Humana
   */
  public static CreateColaboradorDTO humana(String tipo,
                                            String nombre,
                                            String apellido,
                                            String fechaNacimiento,
                                            String barrio,
                                            String calle,
                                            String altura,
                                            String telefono,
                                            String formaDeColaborar) {
    return CreateColaboradorDTO.builder()
        .tipo(tipo)
        .nombre(nombre)
        .apellido(apellido)
        .fechaNacimiento(fechaNacimiento)
        .barrio(barrio)
        .calle(calle)
        .altura(altura)
        .telefono(telefono)
        .formaDeColaborar(formaDeColaborar)
        .build();
  }

  /**
   * Builder del DTO Colaborador con los atributos de un Colaborador Juridico.
   *
   * @param razonSocial      Razonsocial del Colaborador
   * @param tipoRazonSocial  Tipo de Razonsocial del Colaborador
   * @param rubro            Rubro del Colaborador
   * @param barrio           Barrio de la Dirección del Colaborador
   * @param calle            Calle de la Dirección del Colaborador
   * @param altura           Altura de la Dirección del Colaborador
   * @param telefono         Telefono del Colaborador
   * @param formaDeColaborar Formas de Colaborar elegidas por el Colaborador
   * @return DTO Create Colaborador en formato Juridico
   */
  public static CreateColaboradorDTO juridica(String tipo,
                                              String razonSocial,
                                              String tipoRazonSocial,
                                              String rubro,
                                              String barrio,
                                              String calle,
                                              String altura,
                                              String telefono,
                                              String formaDeColaborar) {
    return CreateColaboradorDTO.builder()
        .tipo(tipo)
        .razonSocial(razonSocial)
        .tipoRazonSocial(tipoRazonSocial)
        .rubro(rubro)
        .barrio(barrio)
        .calle(calle)
        .altura(altura)
        .telefono(telefono)
        .formaDeColaborar(formaDeColaborar)
        .build();
  }
}


