package ar.edu.utn.frba.dds.models.tecnico;

import ar.edu.utn.frba.dds.models.data.Imagen;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "visita_tenico")
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

  @Column(name = "fecha_hora", nullable = false)
  private LocalDateTime fechaHora;

  @Column(name = "descripcion", nullable = false)
  private String descripcion;

  @Embedded
  private Imagen foto;

  @Column(name = "fue_resuelta", nullable = false)
  private Boolean fallaResuelta;

  public static VisitaTecnico by(Tecnico tecnico,
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

  public static VisitaTecnico by(Tecnico tecnico,
                                 Heladera heladera) {
    return VisitaTecnico
        .builder()
        .tecnico(tecnico)
        .heladera(heladera)
        .build();
  }
}
