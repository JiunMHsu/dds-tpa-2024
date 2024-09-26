package ar.edu.utn.frba.dds.models.entities.tecnico;

import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "visita_tecnico")
public class VisitaTecnico {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tecnico_id", nullable = false)
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;

    @Column(name = "fecha_hora", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Embedded
    private Imagen foto;

    @Column(name = "fue_resuelta", nullable = false)
    private Boolean fallaResuelta;

    public static VisitaTecnico por(Tecnico tecnico,
                                    Heladera heladera,
                                    LocalDateTime fechaHora,
                                    String descripcion,
                                    Imagen foto,
                                    Boolean fallaResuelta) {
        return VisitaTecnico
                .builder()
                .tecnico(tecnico)
                .heladera(heladera)
                .fechaHora(fechaHora)
                .descripcion(descripcion)
                .foto(foto)
                .fallaResuelta(fallaResuelta)
                .build();
    }

    public static VisitaTecnico por(Tecnico tecnico,
                                    Heladera heladera) {
        return VisitaTecnico
                .builder()
                .tecnico(tecnico)
                .heladera(heladera)
                .build();
    }
}
