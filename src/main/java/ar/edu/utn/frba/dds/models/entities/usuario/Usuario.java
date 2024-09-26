package ar.edu.utn.frba.dds.models.entities.usuario;

import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario extends EntidadPersistente {

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    public static Usuario con(String nombreUsuario, String contrasenia, String email) {
        return Usuario
                .builder()
                .nombre(nombreUsuario)
                .contrasenia(contrasenia)
                .email(email)
                .build();
    }

    public static Usuario conEmail(String email) {
        return Usuario
                .builder()
                .email(email)
                .build();
    }

    public static Usuario vacio() {
        return Usuario
                .builder()
                .build();
    }
}