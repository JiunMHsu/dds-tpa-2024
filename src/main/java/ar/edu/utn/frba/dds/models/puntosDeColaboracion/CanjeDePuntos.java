package ar.edu.utn.frba.dds.models.puntosDeColaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
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
@Table(name = "canje_puntos")
public class CanjeDePuntos {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "colaborador_id", nullable = false)
  private Colaborador colaborador;

  @Column(name = "fecha_canjeo", nullable = false)
  private LocalDate fechaCanjeo;

  @Column(name = "puntos_canjeados", nullable = false)
  private Double puntosCanjeados;

  @Column(name = "puntos_restamtes", nullable = false)
  private Double puntosRestantes;

  @ManyToOne
  @JoinColumn(name = "oferta_id", nullable = false)
  private OfertaDeProductos oferta;

  public static CanjeDePuntos por(Colaborador colaborador,
                                  LocalDate fechaCanjeo,
                                  Double puntosCanjeados,
                                  Double puntosRestantes,
                                  OfertaDeProductos oferta) {
    return CanjeDePuntos
        .builder()
        .colaborador(colaborador)
        .fechaCanjeo(fechaCanjeo)
        .puntosCanjeados(puntosCanjeados)
        .puntosRestantes(puntosRestantes)
        .oferta(oferta)
        .build();
  }
}
