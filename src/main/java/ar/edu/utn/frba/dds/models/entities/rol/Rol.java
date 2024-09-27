package ar.edu.utn.frba.dds.models.entities.rol;

import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table (name = "rol")
public class Rol extends EntidadPersistente {

    // Hice que se extienda de EntidadPersistente xq quizas puede ser util que se de la baja logica

    @Column (name = "nombre", unique = true, nullable = false)
    private String nombre;

    @OneToMany
    @JoinColumn(name = "id") // lo dejo asi nomas x ahora, c q no es lo mejor
    private List<Permiso> permisos;

    public static Rol con(String nombre, List<Permiso> permisos) {
        return Rol
                .builder()
                .nombre(nombre)
                .permisos(permisos)
                .build();
    }

    public static Rol vacio() {
        return Rol
                .builder()
                .build();
    }
}
