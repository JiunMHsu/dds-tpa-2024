package ar.edu.utn.frba.dds.models.entities.usuario;

import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "usuario")
public class Usuario extends EntidadPersistente {

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "clave", nullable = false)
  private String contrasenia;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_rol")
  private TipoRol rol;

  public static Usuario con(String nombreUsuario, String contrasenia, String email, TipoRol rol) {
    return Usuario
        .builder()
        .nombre(nombreUsuario)
        .contrasenia(contrasenia)
        .email(email)
        .rol(rol)
        .build();
  }

  public static Usuario conEmail(String email) {
    return Usuario.con("", "", email, null);
  }

}