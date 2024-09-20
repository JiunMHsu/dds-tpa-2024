package ar.edu.utn.frba.dds.models.incidente;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "incidente")
@DiscriminatorColumn(name = "tipo")
public abstract class Incidente extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    protected Heladera heladera;

    @Column(name = "fecha_hora")
    protected LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_incidente")
    protected TipoIncidente tipoIncidente;

}
