package ar.edu.utn.frba.dds.dtos.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.colaborador.TipoColaborador;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class ColaboradorDTO {

  private String id;

  private String tipoColaborador;

  private String contacto; // pueden ser cuatro

  private String direccion; // concatenar

  private String formaDeColaborar; //concatenar

  private String razonSocial;

  private String tipoRazonSocial;

  private String rubro;

  private String nombre;

  private String apellido;

  private String fechaNacimiento;

  private String mail;

  private String documento;

  private Boolean isHumano;


  public static ColaboradorDTO completa(Colaborador colaborador) {

    if (colaborador.getTipoColaborador().equals(TipoColaborador.HUMANO)) {
      return ColaboradorDTO.completaHumano(colaborador);
    }
    return ColaboradorDTO.completaJuridico(colaborador);

  }

  public static ColaboradorDTO completaHumano(Colaborador colaborador) {

    String formasDeColaborar = colaborador.getFormaDeColaborar()
        .stream()
        .map(TipoColaboracion::getDescription)
        .collect(Collectors.joining(", "));

    String domicilioString = colaborador.getDireccion().getCalle().getNombre() + " " + colaborador.getDireccion().getAltura().toString();
    //String contactos = colaborador.getContacto().getTelegram() + " " + colaborador.getContacto().getEmail() + " " + colaborador.getContacto().getWhatsApp() + " " + colaborador.getContacto().getTelefono();
    // TODO si alguno de los contactos es null chequearlo porque sino imprime los nulls creo
    return ColaboradorDTO
        .builder()
        .nombre(colaborador.getNombre())
        .apellido(colaborador.getApellido())
        .fechaNacimiento(colaborador.getFechaNacimiento().toString())
        .direccion(domicilioString)
        .formaDeColaborar(formasDeColaborar)
        // .contacto(contactos)
        .tipoColaborador("HUMANO")
        .build();
  }

  public static ColaboradorDTO completaJuridico(Colaborador colaborador) {

    String formasDeColaborar = colaborador.getFormaDeColaborar()
        .stream()
        .map(TipoColaboracion::getDescription)
        .collect(Collectors.joining(", "));

    String domicilioString = colaborador.getDireccion().getCalle().getNombre() + " " + colaborador.getDireccion().getAltura().toString();
    //String contactos = colaborador.getContacto().getTelegram() + " " + colaborador.getContacto().getEmail() + " " + colaborador.getContacto().getWhatsApp() + " " + colaborador.getContacto().getTelefono();
    // TODO si alguno de los contactos es null chequearlo porque sino imprime los nulls creo
    return ColaboradorDTO
        .builder()
        .direccion(domicilioString)
        .rubro(colaborador.getRubro())
        .formaDeColaborar(formasDeColaborar)
        //.contacto(contactos)
        .razonSocial(colaborador.getRazonSocial())
        .tipoRazonSocial(colaborador.getTipoRazonSocial().name())
        .tipoColaborador("JURIDICO")
        .build();
  }

  public static ColaboradorDTO preview(Colaborador colaborador) {
    if (colaborador.getTipoColaborador().equals(TipoColaborador.HUMANO)) {
      return ColaboradorDTO.previewHumano(colaborador);
    } else {
      return ColaboradorDTO.previewJuridico(colaborador);
    }
  }

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
