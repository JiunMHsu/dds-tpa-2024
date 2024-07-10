package ar.edu.utn.frba.dds.models.colaborador;

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
public class Colaborador {

  private TipoColaborador tipoColaborador;
  private Usuario usuario;
  private Contacto contacto;
  private Direccion direccion;
  private FormularioRespondido datosAdicionales;
  private List<TipoColaboracion> formaDeColaborar;
  private String razonSocial;
  private TipoRazonSocial tipoRazonSocial;
  private String rubro;
  private String nombre;
  private String apellido;
  private LocalDate fechaNacimiento;


  // private Double puntosObtenidos;
  // private Double puntosCanjeados;

  public static Colaborador juridica(Usuario usuario, String razonSocial, TipoRazonSocial tipoRazonSocial, String rubro, Contacto contacto, Direccion direccion, List<TipoColaboracion> formaDeColaborar) {
    return Colaborador
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

  public static Colaborador juridica(Usuario usuario, String razonSocial, TipoRazonSocial tipoRazonSocial, Contacto contacto, Direccion direccion) {
    return Colaborador
        .builder()
        .usuario(usuario)
        .razonSocial(razonSocial)
        .tipoRazonSocial(tipoRazonSocial)
        .contacto(contacto)
        .direccion(direccion)
        .build();
  }

  public static Colaborador humana(Usuario usuario, String nombre, String apellido, LocalDate fechaNacimiento, Contacto contacto, Direccion direccion, List<TipoColaboracion> formaDeColaborar) {
    return Colaborador
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

  public static Colaborador humana(Usuario usuario, String nombre, String apellido, LocalDate fechaNacimiento) {
    return Colaborador
        .builder()
        .usuario(usuario)
        .nombre(nombre)
        .apellido(apellido)
        .fechaNacimiento(fechaNacimiento)
        .build();
  }

  public static Colaborador persona(Usuario usuario) {
    return Colaborador
        .builder()
        .usuario(usuario)
        .build();
  }

  public static Colaborador persona() {
    return Colaborador
        .builder()
        .build();
  }
}
