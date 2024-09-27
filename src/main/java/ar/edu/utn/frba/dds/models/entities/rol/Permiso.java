package ar.edu.utn.frba.dds.models.entities.rol;

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
@Table(name = "permiso")
public class Permiso {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;
}
