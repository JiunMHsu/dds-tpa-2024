package ar.edu.utn.frba.dds.models.entities.rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "permiso")
public class Permiso {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;
}
