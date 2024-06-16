package ar.edu.utn.frba.dds.models.usuario;

import ar.edu.utn.frba.dds.models.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.TipoRazonSocial;
import ar.edu.utn.frba.dds.models.formulario.FormularioRespondido;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Persona {

  private Direccion direccion;
  private List<TipoColaboracion> formaDeColaborar;
  private FormularioRespondido datosAdicionales;
  private Usuario usuario;
  private Contacto contacto;
  private TipoDePersona tipoDePersona;
  private Double puntosObtenidos;
  private Double puntosCanjeados;
  private String razonSocial;
  private TipoRazonSocial tipoRazonSocial;
  private String rubro;
  private String nombre;
  private String apellido;
  private LocalDate fechaNacimiento;

  public static Persona juridica(Usuario usuario, String razonSocial, TipoRazonSocial tipoRazonSocial, String rubro, Contacto contacto, Direccion direccion, List<TipoColaboracion> formaDeColaborar) {
    return Persona
        .builder()
        .usuario(usuario)
        .razonSocial(razonSocial)
        .tipoRazonSocial(tipoRazonSocial)
        .rubro(rubro)
        .contacto(contacto)
        .direccion(direccion)
        .formaDeColaborar(formaDeColaborar)
        .build();
  }

  public static Persona juridica(Usuario usuario, String razonSocial, TipoRazonSocial tipoRazonSocial, Contacto contacto, Direccion direccion) {
    return Persona
        .builder()
        .usuario(usuario)
        .razonSocial(razonSocial)
        .tipoRazonSocial(tipoRazonSocial)
        .contacto(contacto)
        .direccion(direccion)
        .build();
  }

  public static Persona humana(Usuario usuario, String nombre, String apellido, LocalDate fechaNacimiento, Contacto contacto, Direccion direccion, List<TipoColaboracion> formaDeColaborar) {
    return Persona
        .builder()
        .usuario(usuario)
        .nombre(nombre)
        .apellido(apellido)
        .fechaNacimiento(fechaNacimiento)
        .contacto(contacto)
        .direccion(direccion)
        .formaDeColaborar(formaDeColaborar)
        .build();
  }

  public static Persona humana(Usuario usuario, String nombre, String apellido, LocalDate fechaNacimiento) {
    return Persona
        .builder()
        .usuario(usuario)
        .nombre(nombre)
        .apellido(apellido)
        .fechaNacimiento(fechaNacimiento)
        .build();
  }

  public static Persona persona(Usuario usuario) {
    return Persona
        .builder()
        .usuario(usuario)
        .build();
  }

  public static Persona persona() {
    return Persona
        .builder()
        .build();
  }
}
