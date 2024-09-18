package ar.edu.utn.frba.dds.models.usuario;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "contrasenia")
    private String contrasenia;

    @Column(name = "email")
    private String email;

    public static Usuario with(String nombreUsuario, String contrasenia, String email) {
        return Usuario
                .builder()
                .nombre(nombreUsuario)
                .contrasenia(contrasenia)
                .email(email)
                .build();
    }

    public static Usuario withEmail(String email) {
        return Usuario
                .builder()
                .email(email)
                .build();
    }

    public static Usuario empty() {
        return Usuario
                .builder()
                .build();
    }
}