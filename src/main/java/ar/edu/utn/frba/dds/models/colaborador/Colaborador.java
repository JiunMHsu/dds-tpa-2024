package ar.edu.utn.frba.dds.models.colaborador;

import ar.edu.utn.frba.dds.models.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.TipoRazonSocial;
import ar.edu.utn.frba.dds.models.formulario.FormularioRespondido;
import ar.edu.utn.frba.dds.models.usuario.Usuario;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "colaborador")
public class Colaborador {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  private TipoColaborador tipoColaborador;

  @OneToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  private Contacto contacto; // Embedded
  private Direccion direccion; // Embedded
  private FormularioRespondido datosAdicionales;

  @ElementCollection
  @CollectionTable(name = "formas_de_colaborar", joinColumns = @JoinColumn(name = "colaborador_id"))
  @Column(name = "colaboracion")
  private List<Colaboracion> formaDeColaborar;

  @Column(name = "razon_social")
  private String razonSocial;

  @Column(name = "tipo")
  private TipoRazonSocial tipoRazonSocial;

  @Column(name = "rubro")
  private String rubro;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "apellido")
  private String apellido;

  @Column(name = "fecha_nacimiento", columnDefinition = "DATE")
  private LocalDate fechaNacimiento;

  public static Colaborador juridica(Usuario usuario, String razonSocial, TipoRazonSocial tipoRazonSocial, String rubro, Contacto contacto, Direccion direccion, List<Colaboracion> formaDeColaborar) {
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

  public static Colaborador humana(Usuario usuario, String nombre, String apellido, LocalDate fechaNacimiento, Contacto contacto, Direccion direccion, List<Colaboracion> formaDeColaborar) {
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

  public static Colaborador colaborador(Usuario usuario) {
    return Colaborador
        .builder()
        .usuario(usuario)
        .build();
  }

}
