package ar.edu.utn.frba.dds.models.entities.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "distribucion_viandas")
public class DistribucionViandas extends EntidadPersistente {

  @ManyToOne
  @JoinColumn(name = "colaborador_id", nullable = false)
  private Colaborador colaborador;

  @Column(name = "fecha_hora", columnDefinition = "DATETIME", nullable = false)
  private LocalDateTime fechaHora;

  @ManyToOne
  @JoinColumn(name = "heladera_origen_id") // nullable por compatibilidad
  private Heladera origen;

  @ManyToOne
  @JoinColumn(name = "heladera_destino_id") // nullable por compatibilidad
  private Heladera destino;

  @Column(name = "cant_viandas", columnDefinition = "SMALLINT", nullable = false)
  private Integer viandas;

  @Column(name = "motivo", columnDefinition = "TEXT")
  private String motivo;

}