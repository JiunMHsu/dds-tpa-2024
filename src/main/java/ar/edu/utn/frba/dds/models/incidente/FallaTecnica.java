package ar.edu.utn.frba.dds.models.incidente;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
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
@Table(name = "falla_tecnica")
public class FallaTecnica implements Incidente {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  @Column (name = "fecha_hora")
  private LocalDateTime fechaHora;

  @ManyToOne
  @JoinColumn(name = "colaborador_id", nullable = false)
  private Colaborador colaborador;

  @Column (name = "descripcion", columnDefinition = "TEXT")
  private String descripcion;

  @Embedded
  private Imagen foto;

  public static FallaTecnica de(Heladera heladera,
                                LocalDateTime fechaHora,
                                Colaborador colaborador,
                                String descripcion,
                                Imagen foto) {
    return FallaTecnica
        .builder()
        .heladera(heladera)
        .fechaHora(fechaHora)
        .colaborador(colaborador)
        .descripcion(descripcion)
        .foto(foto)
        .build();
  }

  public static FallaTecnica de(Heladera heladera, Colaborador colaborador) {
    return FallaTecnica
        .builder()
        .colaborador(colaborador)
        .fechaHora(LocalDateTime.now())
        .heladera(heladera)
        .build();
  }

  public TipoIncidente getTipo() {
    return TipoIncidente.FALLA_TECNICA;
  }
}
