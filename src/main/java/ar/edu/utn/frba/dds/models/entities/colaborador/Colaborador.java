package ar.edu.utn.frba.dds.models.entities.colaborador;

import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.Puntos;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.PuntosInvalidosException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.TipoRazonSocial;
import ar.edu.utn.frba.dds.models.entities.formulario.FormularioRespondido;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

  @Embedded
  private Contacto contacto;

  @Embedded
  private Direccion direccion;

  @OneToOne
  @JoinColumn(name = "formulario_respondido_id")
  private FormularioRespondido datosAdicionales;

  @ElementCollection
  @CollectionTable(name = "formas_de_colaborar", joinColumns = @JoinColumn(name = "colaborador_id"))
  @Column(name = "colaboracion")
  private List<TipoColaboracion> formaDeColaborar;

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

  public static Colaborador juridica(Usuario usuario,
                                     String razonSocial,
                                     TipoRazonSocial tipoRazonSocial,
                                     String rubro,
                                     Contacto contacto,
                                     Direccion direccion,
                                     ArrayList<TipoColaboracion> formaDeColaborar,
                                     Puntos puntos) {
    return Colaborador.builder()
        .tipoColaborador(TipoColaborador.JURIDICO)
        .usuario(usuario)
        .razonSocial(razonSocial)
        .tipoRazonSocial(tipoRazonSocial)
        .rubro(rubro)
        .contacto(contacto)
        .direccion(direccion)
        .formaDeColaborar(formaDeColaborar)
        .puntos(puntos)
        .build();
  }

  public static Colaborador humana(Usuario usuario,
                                   String nombre,
                                   String apellido,
                                   Documento documento,
                                   LocalDate fechaNacimiento,
                                   Contacto contacto,
                                   Direccion direccion,
                                   ArrayList<TipoColaboracion> formaDeColaborar,
                                   Puntos puntos) {
    return Colaborador.builder()
        .tipoColaborador(TipoColaborador.HUMANO)
        .usuario(usuario)
        .nombre(nombre)
        .apellido(apellido)
        .documento(documento)
        .fechaNacimiento(fechaNacimiento)
        .contacto(contacto)
        .direccion(direccion)
        .formaDeColaborar(formaDeColaborar)
        .puntos(puntos)
        .build();
  }

  public static Colaborador humana(Usuario usuario,
                                   String nombre,
                                   String apellido,
                                   LocalDate fechaNacimiento) {
    return Colaborador.humana(usuario, nombre, apellido, null, fechaNacimiento, null, null, new ArrayList<>(), new Puntos(0, false, null));
  }

  public static Colaborador humanaDocumento(Usuario usuario,
                                            String nombre,
                                            String apellido,
                                            Documento documento) {
    return Colaborador.humana(usuario, nombre, apellido, documento, null, null, null, new ArrayList<>(), new Puntos(0, false, null));
  }

  public static Colaborador colaborador(Usuario usuario) {
    return Colaborador.builder().usuario(usuario).build();
  }

  public static Colaborador colaborador(Usuario usuario,
                                        Contacto contacto,
                                        Direccion direccion,
                                        ArrayList<TipoColaboracion> formaDeColaborar) {
    return Colaborador.builder()
        .usuario(usuario)
        .contacto(contacto)
        .direccion(direccion)
        .formaDeColaborar(formaDeColaborar)
        .build();
  }

  public boolean puedeColaborar(TipoColaboracion tipoColaboracion) {
    return this.formaDeColaborar.contains(tipoColaboracion);
  }

  public double puntos() throws PuntosInvalidosException {
    if (!puntos.esValido(tipoColaborador)) throw new PuntosInvalidosException();
    return puntos.getPuntos();
  }

  public void invalidarPuntos() {
    puntos.setEsValido(false);
  }
}
