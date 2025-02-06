package ar.edu.utn.frba.dds.dtos.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.colaborador.TipoColaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de Colaborador.
 */
@Getter
@Setter
@Builder
public class ColaboradorDTO {
  private final String userId;
  private final String id;
  private final String tipoColaborador;
  private final String contacto;
  private final String direccion;
  private final String formaDeColaborar;
  private final String razonSocial;
  private final String tipoRazonSocial;
  private final String rubro;
  private final String nombre;
  private final String apellido;
  private final String fechaNacimiento;
  private final String mail;
  private final String documento;
  private final Boolean isHumano;

  /**
   * Genera el DTO completo de un Colaborador.
   *
   * @param colaborador Colaborador
   * @return ColaboradorDTO
   */
  public static ColaboradorDTO completa(Colaborador colaborador) {

    if (colaborador.getTipoColaborador().equals(TipoColaborador.HUMANO)) {
      return ColaboradorDTO.completaHumano(colaborador);
    }
    return ColaboradorDTO.completaJuridico(colaborador);

  }

  /**
   * Genera el DTO completo de un Colaborador Humano.
   *
   * @param colaborador Colaborador
   * @return ColaboradorDTO
   */
  public static ColaboradorDTO completaHumano(Colaborador colaborador) {

    String formasDeColaborar = colaborador.getFormasDeColaborar()
        .stream()
        .map(TipoColaboracion::getDescription)
        .collect(Collectors.joining(", "));

    String contactos = colaborador.getContactos().stream()
        .map(Contacto::getValor)
        .filter(Objects::nonNull)
        .collect(Collectors.joining(" "));

    return ColaboradorDTO
        .builder()
        .nombre(colaborador.getNombre())
        .apellido(colaborador.getApellido())
        .fechaNacimiento(colaborador.getFechaNacimiento().toString())
        .direccion(colaborador.getDireccion().toString())
        .formaDeColaborar(formasDeColaborar)
        .contacto(contactos)
        .tipoColaborador("HUMANO")
        .build();
  }

  /**
   * Genera el DTO completo de un Colaborador Juridico.
   *
   * @param colaborador Colaborador
   * @return ColaboradorDTO
   */
  public static ColaboradorDTO completaJuridico(Colaborador colaborador) {

    String formasDeColaborar = colaborador.getFormasDeColaborar()
        .stream()
        .map(TipoColaboracion::getDescription)
        .collect(Collectors.joining(", "));

    String contactos = colaborador.getContactos().stream()
        .map(Contacto::getValor)
        .filter(Objects::nonNull)
        .collect(Collectors.joining(" "));

    return ColaboradorDTO
        .builder()
        .direccion(colaborador.getDireccion().toString())
        .rubro(colaborador.getRubro())
        .formaDeColaborar(formasDeColaborar)
        .contacto(contactos)
        .razonSocial(colaborador.getRazonSocial())
        .tipoRazonSocial(colaborador.getTipoRazonSocial().name())
        .tipoColaborador("JURIDICO")
        .build();
  }

  /**
   * Genera el DTO de la preview de un Colaborador.
   *
   * @param colaborador Colaborador
   * @return ColaboradorDTO
   */
  public static ColaboradorDTO preview(Colaborador colaborador) {
    if (colaborador.getTipoColaborador().equals(TipoColaborador.HUMANO)) {
      return ColaboradorDTO.previewHumano(colaborador);
    } else {
      return ColaboradorDTO.previewJuridico(colaborador);
    }
  }

  /**
   * Genera el DTO de la preview de un Colaborador Humano.
   *
   * @param colaborador Colaborador
   * @return ColaboradorDTO
   */
  public static ColaboradorDTO previewHumano(Colaborador colaborador) {

    String numeroDocumento = colaborador.getDocumento() != null
        ? colaborador.getDocumento().getNumero()
        : "Indocumentado";

    return ColaboradorDTO
        .builder()
        .nombre(colaborador.getNombre())
        .apellido(colaborador.getApellido())
        .mail(colaborador.getUsuario().getEmail())
        .documento(numeroDocumento)
        .tipoColaborador("HUMANO")
        .isHumano(true)
        .build();
  }

  /**
   * Genera el DTO de la preview de un Colaborador Juridico.
   *
   * @param colaborador Colaborador
   * @return ColaboradorDTO
   */
  public static ColaboradorDTO previewJuridico(Colaborador colaborador) {

    return ColaboradorDTO
        .builder()
        .rubro(colaborador.getRubro())
        .razonSocial(colaborador.getRazonSocial())
        .tipoRazonSocial(colaborador.getTipoRazonSocial().name())
        .tipoColaborador("JURIDICO")
        .isHumano(false)
        .build();
  }
}
