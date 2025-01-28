package ar.edu.utn.frba.dds.models.entities.sugerencia;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Modelo SugerenciaTrasladoVianda.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sugerencia_traslado_vianda")
public class SugerenciaTrasladoVianda extends EntidadPersistente {

  @ManyToOne
  @JoinColumn(name = "heladera_origen_id", nullable = false)
  private Heladera heladeraOrigen;

  @OneToMany
  @JoinColumn(name = "sugerencia_traslado_id")
  private List<Heladera> heladerasDestino;

  @ManyToOne
  @JoinColumn(name = "colaborador_id") // se setea una vez que se acepta la sugerencia
  private Colaborador colaborador;

  @Enumerated(EnumType.STRING)
  @Column(name = "estado", nullable = false)
  private EstadoSugerencia estado;

  @OneToOne
  @JoinColumn(name = "incidente_causa_id")
  private Incidente causa;

  /**
   * Crea una sugerencia de traslado de vianda con estado inicial.
   *
   * @param heladeraOrigen   Heladera origen.
   * @param heladerasDestino Heladeras destino.
   * @param causa            Causa.
   * @return SugerenciaTrasladoVianda.
   */
  public static SugerenciaTrasladoVianda de(Heladera heladeraOrigen,
                                            List<Heladera> heladerasDestino,
                                            Incidente causa) {
    return SugerenciaTrasladoVianda
        .builder()
        .heladeraOrigen(heladeraOrigen)
        .heladerasDestino(heladerasDestino)
        .colaborador(null)
        .estado(EstadoSugerencia.PENDIENTE)
        .causa(causa)
        .build();
  }

  /**
   * Verifica si la sugerencia sigue pendiente.
   *
   * @return boolean.
   */
  public boolean siguePendiente() {
    return this.estado.equals(EstadoSugerencia.PENDIENTE) && this.colaborador == null;
  }

  /**
   * Acepta la sugerencia.
   *
   * @param colaborador Colaborador.
   */
  public void aceptadoPor(Colaborador colaborador) {
    this.colaborador = colaborador;
    this.estado = EstadoSugerencia.ACEPTADA;
  }
}
