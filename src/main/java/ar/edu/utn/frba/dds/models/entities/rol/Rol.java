package ar.edu.utn.frba.dds.models.entities.rol;

import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "rol")
public class Rol extends EntidadPersistente {

    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre;

    @OneToMany
    @JoinColumn(name = "rol_id")
    private List<Permiso> permisos;

    public static Rol con(String nombre, List<Permiso> permisos) {
        return Rol
                .builder()
                .nombre(nombre)
                .permisos(permisos)
                .build();
    }

    public void agregarPermiso(Permiso permiso) {
        this.permisos.add(permiso);
    }

    public void eliminarPermiso(Permiso permiso) {
        this.permisos.remove(permiso);
    }

    public Boolean tienePermiso(Permiso permiso) {
        return this.permisos.contains(permiso);
    }
}
