package ar.edu.utn.frba.dds.models.puntosDeColaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
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
@Table(name = "canjePuntos")
public class CanjeDePuntos {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @OneToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id", unique = true)
  private Colaborador colaborador;

  @Column (name = "fecha_canjeo")
  private LocalDate fechaCanjeo;

  @Column (name = "puntos_canjeados")
  private Double puntosCanjeados;

  @Column (name = "puntos_restamtes")
  private Double puntosRestantes;

  @ManyToOne
  @JoinColumn(name = "oferta_id", referencedColumnName = "id", unique = true)
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
