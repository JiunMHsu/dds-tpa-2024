package ar.edu.utn.frba.dds.models.entities.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import java.time.LocalDateTime;
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
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

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

    // @ManyToOne
    // @JoinColumn(name = "variante_usado", nullable = false)
    // private VarianteDePuntos varianteUsado;

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
