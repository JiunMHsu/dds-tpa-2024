package ar.edu.utn.frba.dds.models.entities.tecnico;

import ar.edu.utn.frba.dds.models.entities.data.Area;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo Tecnico.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tecnico")
public class Tecnico extends EntidadPersistente {

  @OneToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "apellido", nullable = false)
  private String apellido;

  @Embedded
  private Documento documento;

  @Column(name = "cuit", unique = true, nullable = false)
  private String cuit;

  @OneToOne
  @JoinColumn(name = "contacto_id", nullable = false)
  private Contacto contacto;

  @Embedded
  private Area areaDeCobertura;

  /**
   * Crea un técnico.
   *
   * @param usuario         Usuario.
   * @param nombre          Nombre.
   * @param apellido        Apellido.
   * @param documento       Documento.
   * @param cuit            CUIT.
   * @param contacto        Contacto.
   * @param areaDeCobertura Área de cobertura.
   * @return Técnico.
   */
  public static Tecnico con(Usuario usuario,
                            String nombre,
                            String apellido,
                            Documento documento,
                            String cuit,
                            Contacto contacto,
                            Area areaDeCobertura) {

    return Tecnico
        .builder()
        .usuario(usuario)
        .nombre(nombre)
        .apellido(apellido)
        .documento(documento)
        .cuit(cuit)
        .contacto(contacto)
        .areaDeCobertura(areaDeCobertura)
        .build();
  }

}
