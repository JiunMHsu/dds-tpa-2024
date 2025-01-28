package ar.edu.utn.frba.dds.models.entities.tecnico;

import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Modelo VisitaTecnica.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "visita_tecnica")
public class VisitaTecnica extends EntidadPersistente {

  @ManyToOne
  @JoinColumn(name = "tecnico_id", nullable = false)
  private Tecnico tecnico;

  @ManyToOne
  @JoinColumn(name = "incidente_id", nullable = false)
  private Incidente incidente;

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  @Column(name = "fecha_hora", columnDefinition = "DATETIME", nullable = false)
  private LocalDateTime fechaHora;

  @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
  private String descripcion;

  @Column(name = "pudo_resolverse", nullable = false)
  private boolean pudoResolverse;

  @Embedded
  private Imagen foto;

  /**
   * Crea una visita técnica.
   *
   * @param tecnico        {@link Tecnico} que realizó la visita.
   * @param incidente      {@link Incidente} que motivó la visita.
   * @param heladera       {@link Heladera} que fue inspeccionada.
   * @param fechaHora      Fecha y hora de la visita.
   * @param descripcion    Descripción de la visita.
   * @param pudoResolverse Indica si el incidente pudo resolverse.
   * @param foto           {@link Imagen} de la visita.
   * @return Visita técnica.
   */
  public static VisitaTecnica por(Tecnico tecnico,
                                  Incidente incidente,
                                  Heladera heladera,
                                  LocalDateTime fechaHora,
                                  String descripcion,
                                  boolean pudoResolverse,
                                  Imagen foto
  ) {
    return VisitaTecnica
        .builder()
        .tecnico(tecnico)
        .incidente(incidente)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .descripcion(descripcion)
        .pudoResolverse(pudoResolverse)
        .foto(foto)
        .build();
  }

}
