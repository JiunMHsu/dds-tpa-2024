package ar.edu.utn.frba.dds.models.entities.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
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

/**
 * Clase que representa un Canje de Puntos en el sistema.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "canje_puntos")
public class CanjeDePuntos extends EntidadPersistente {

  @ManyToOne
  @JoinColumn(name = "colaborador_id", nullable = false)
  private Colaborador colaborador;

  @ManyToOne
  @JoinColumn(name = "oferta_id", nullable = false)
  private OfertaDeProductos oferta;

  @Column(name = "fecha_canje", nullable = false)
  private LocalDateTime fechaHora;

  @Column(name = "puntos_canjeados", nullable = false)
  private double puntosCanjeados;

  @Column(name = "puntos_restates", nullable = false)
  private double puntosRestantes;

  /**
   * Constructor de la clase CanjeDePuntos.
   *
   * @param colaborador     Colaborador que realiza el canje.
   * @param oferta          Oferta de productos que se canjea.
   * @param fechaCanjeo     Fecha y hora en la que se realiza el canje.
   * @param puntosCanjeados Puntos canjeados en el canje.
   * @param puntosRestantes Puntos restantes del colaborador luego del canje.
   * @return CanjeDePuntos.
   */
  public static CanjeDePuntos por(Colaborador colaborador,
                                  OfertaDeProductos oferta,
                                  LocalDateTime fechaCanjeo,
                                  double puntosCanjeados,
                                  double puntosRestantes
  ) {
    return CanjeDePuntos.builder()
        .colaborador(colaborador)
        .oferta(oferta)
        .fechaHora(fechaCanjeo)
        .puntosCanjeados(puntosCanjeados)
        .puntosRestantes(puntosRestantes)
        .build();
  }
}
