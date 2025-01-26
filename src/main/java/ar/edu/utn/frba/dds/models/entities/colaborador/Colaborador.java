package ar.edu.utn.frba.dds.models.entities.colaborador;

import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.Puntos;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.PuntosInvalidosException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.TipoRazonSocial;
import ar.edu.utn.frba.dds.models.entities.formulario.FormularioRespondido;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo Colaborador.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "colaborador")
public class Colaborador extends EntidadPersistente {

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_colaborador")
  private TipoColaborador tipoColaborador;

  @OneToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @OneToMany
  @JoinColumn(name = "contacto_id", referencedColumnName = "id")
  private List<Contacto> contactos;

  @Embedded
  private Direccion direccion;

  @OneToOne
  @JoinColumn(name = "formulario_respondido_id")
  private FormularioRespondido datosAdicionales;

  @ElementCollection
  @CollectionTable(name = "formas_de_colaborar", joinColumns = @JoinColumn(name = "colaborador_id"))
  @Column(name = "colaboracion")
  private List<TipoColaboracion> formasDeColaborar;

  @Column(name = "razon_social")
  private String razonSocial;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo")
  private TipoRazonSocial tipoRazonSocial;

  @Column(name = "rubro")
  private String rubro;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "apellido")
  private String apellido;

  @Embedded
  private Documento documento;

  @Column(name = "fecha_nacimiento", columnDefinition = "DATE")
  private LocalDate fechaNacimiento;

  @Embedded
  private Puntos puntos;

  /**
   * Constructor de un colaborador jurídico.
   *
   * @param usuario           usuario
   * @param razonSocial       razón social
   * @param tipoRazonSocial   tipo de razón social
   * @param rubro             rubro
   * @param contactos         lista de contactos
   * @param direccion         dirección
   * @param formasDeColaborar formas de colaborar
   * @param puntos            puntos
   */
  public static Colaborador juridica(Usuario usuario,
                                     String razonSocial,
                                     TipoRazonSocial tipoRazonSocial,
                                     String rubro,
                                     List<Contacto> contactos,
                                     Direccion direccion,
                                     ArrayList<TipoColaboracion> formasDeColaborar,
                                     Puntos puntos) {
    return Colaborador.builder()
        .tipoColaborador(TipoColaborador.JURIDICO)
        .usuario(usuario)
        .razonSocial(razonSocial)
        .tipoRazonSocial(tipoRazonSocial)
        .rubro(rubro)
        .contactos(contactos)
        .direccion(direccion)
        .formasDeColaborar(formasDeColaborar)
        .puntos(puntos)
        .build();
  }

  /**
   * Constructor de un colaborador humano.
   *
   * @param usuario           usuario
   * @param nombre            nombre
   * @param apellido          apellido
   * @param documento         documento
   * @param fechaNacimiento   fecha de nacimiento
   * @param contactos         lista de contactos
   * @param direccion         dirección
   * @param formasDeColaborar formas de colaborar
   * @param puntos            puntos
   */
  public static Colaborador humana(Usuario usuario,
                                   String nombre,
                                   String apellido,
                                   Documento documento,
                                   LocalDate fechaNacimiento,
                                   List<Contacto> contactos,
                                   Direccion direccion,
                                   ArrayList<TipoColaboracion> formasDeColaborar,
                                   Puntos puntos) {
    return Colaborador.builder()
        .tipoColaborador(TipoColaborador.HUMANO)
        .usuario(usuario)
        .nombre(nombre)
        .apellido(apellido)
        .documento(documento)
        .fechaNacimiento(fechaNacimiento)
        .contactos(contactos)
        .direccion(direccion)
        .formasDeColaborar(formasDeColaborar)
        .puntos(puntos)
        .build();
  }

  /**
   * Constructor de un colaborador humano con documento.
   *
   * @param usuario   usuario
   * @param nombre    nombre
   * @param apellido  apellido
   * @param documento documento
   */
  public static Colaborador humanaConDocumento(Usuario usuario,
                                               String nombre,
                                               String apellido,
                                               Documento documento) {
    return Colaborador.builder()
        .tipoColaborador(TipoColaborador.HUMANO)
        .usuario(usuario)
        .nombre(nombre)
        .apellido(apellido)
        .documento(documento)
        .formasDeColaborar(new ArrayList<>())
        .puntos(new Puntos(0, false, null))
        .build();
  }

  public static Colaborador conUsuario(Usuario usuario) {
    return Colaborador.builder().usuario(usuario).build();
  }

  /**
   * Constructor de un colaborador.
   *
   * @param usuario           usuario
   * @param contactos         lista de contactos
   * @param direccion         dirección
   * @param formasDeColaborar formas de colaborar
   */
  public static Colaborador colaborador(Usuario usuario,
                                        List<Contacto> contactos,
                                        Direccion direccion,
                                        ArrayList<TipoColaboracion> formasDeColaborar) {
    return Colaborador.builder()
        .usuario(usuario)
        .contactos(contactos)
        .direccion(direccion)
        .formasDeColaborar(formasDeColaborar)
        .build();
  }

  /**
   * Verifica si el colaborador puede colaborar con un tipo de colaboración.
   *
   * @param tipoColaboracion tipo de colaboración
   * @return true si puede colaborar, false en caso contrario
   */
  public boolean puedeColaborar(TipoColaboracion tipoColaboracion) {
    return this.formasDeColaborar.contains(tipoColaboracion);
  }

  /**
   * Retorna los puntos del colaborador.
   * En caso de que los puntos no sean válidos, lanza una excepción.
   *
   * @return puntos
   */
  public double puntos() throws PuntosInvalidosException {
    if (!puntos.esValido(tipoColaborador)) {
      throw new PuntosInvalidosException();
    }
    return puntos.getPuntos();
  }

  public void invalidarPuntos() {
    puntos.setEsValido(false);
  }

  /**
   * Retorna el contacto de un medio de notificación.
   *
   * @param medioDeNotificacion medio de notificación
   * @return contacto
   */
  public Optional<Contacto> getContacto(MedioDeNotificacion medioDeNotificacion) {
    return contactos.stream()
        .filter(contacto -> contacto.getMedioDeNotificacion() == medioDeNotificacion)
        .findFirst();
  }

  public void agregarContacto(Contacto contacto) {
    this.contactos.add(contacto);
  }
}

